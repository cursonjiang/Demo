package com.curson.downloaddemo.service;

import android.content.Context;
import android.content.Intent;

import com.curson.downloaddemo.db.ThreadDAO;
import com.curson.downloaddemo.db.ThreadDAOImpl;
import com.curson.downloaddemo.entities.FileInfo;
import com.curson.downloaddemo.entities.ThreadInfo;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 下载任务类
 * Created by Curson on 15/4/14.
 */
public class DownloadTask {

    private Context mContext;
    private FileInfo mFileInfo;
    private ThreadDAO mThreadDAO;
    private int mFinished = 0;
    public boolean isPause = false;

    public DownloadTask(Context context, FileInfo fileInfo) {
        mContext = context;
        mFileInfo = fileInfo;
        mThreadDAO = new ThreadDAOImpl(context);
    }

    public void download() {
        //读取数据库的线程信息
        List<ThreadInfo> threadInfos = mThreadDAO.getThread(mFileInfo.getUrl());
        ThreadInfo threadInfo;
        if (threadInfos.size() == 0) {
            //初始化线程信息对象
            threadInfo = new ThreadInfo(0, mFileInfo.getLength(), 0, 0, mFileInfo.getUrl());
        } else {
            threadInfo = threadInfos.get(0);
        }
        //创建子线程进行下载
        new DownloadThread(threadInfo).start();
    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread {
        private ThreadInfo mThreadInfo;

        public DownloadThread(ThreadInfo threadInfo) {
            mThreadInfo = threadInfo;
        }

        @Override
        public void run() {
            //向数据库中插入线程信息
            if (!mThreadDAO.isExists(mThreadInfo.getUrl(), mThreadInfo.getId())) {
                mThreadDAO.insertThread(mThreadInfo);
            }
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            RandomAccessFile raf = null;
            try {
                URL url = new URL(mThreadInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                //设置下载位置
                int start = mThreadInfo.getStart() + mThreadInfo.getFinished();
                connection.setRequestProperty("Range", "bytes =" + start + "-" + mThreadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                mFinished += mThreadInfo.getFinished();
                //开始下载
                if (connection.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT) {
                    //读取数据
                    inputStream = connection.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len;
                    long time = System.currentTimeMillis();
                    while ((len = inputStream.read(buffer)) != -1) {
                        //写入文件
                        raf.write(buffer, 0, len);
                        //把下载进度发送广播给Activity
                        mFinished += len;
                        if (System.currentTimeMillis() - time > 500) {
                            time = System.currentTimeMillis();
                            intent.putExtra("finished", mFinished * 100 / mFileInfo.getLength());
                            mContext.sendBroadcast(intent);
                        }
                        //下载暂停时,保存下载进度
                        if (isPause) {
                            mThreadDAO.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mThreadInfo.getFinished());
                            return;
                        }
                    }
                    //下载完成之后删除线程信息
                    mThreadDAO.deleteThread(mThreadInfo.getUrl(), mThreadInfo.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    raf.close();
                    inputStream.close();
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
