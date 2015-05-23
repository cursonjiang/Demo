package com.curson.downloaddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.curson.downloaddemo.entities.FileInfo;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends Service {

    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloads/";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final int MSG_INIT = 0;
    private DownloadTask mDownloadTask;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获得Activity传来的参数
        if (ACTION_START.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.d("test", "Start：" + fileInfo.toString());
            //启动初始化线程
            new InitThread(fileInfo);
        } else if (ACTION_STOP.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.d("test", "Stop：" + fileInfo.toString());
            if (mDownloadTask != null) {
                mDownloadTask.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.d("test", "Init:" + fileInfo);
                    //启动下载任务
                    mDownloadTask = new DownloadTask(DownloadService.this, fileInfo);
                    mDownloadTask.download();
                    break;
            }
        }
    };

    /**
     * 初始化子线程
     */
    class InitThread extends Thread {

        private FileInfo mFileInfo;

        public InitThread(FileInfo fileInfo) {
            mFileInfo = fileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            try {
                //连接网络文件
                URL url = new URL(mFileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                int length = -1;
                if (connection.getResponseCode() == HttpStatus.SC_OK) {
                    //获得文件长度
                    length = connection.getContentLength();
                }
                if (length <= 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                //在本地创建文件
                File file = new File(dir, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");//文件模式,可读可写可删除
                //设置文件长度
                raf.setLength(length);
                mFileInfo.setLength(length);
                mHandler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();//发送到Handler
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    raf.close();
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
