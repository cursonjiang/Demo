package com.curson.downloaddemo.db;

import com.curson.downloaddemo.entities.ThreadInfo;

import java.util.List;

/**
 * 数据访问接口
 * Created by Curson on 15/4/13.
 */
public interface ThreadDAO {

    /**
     * 插入线程信息
     *
     * @param threadInfo 线程信息
     */
    void insertThread(ThreadInfo threadInfo);

    /**
     * 删除线程
     *
     * @param url       线程地址
     * @param thread_id 线程id
     */
    void deleteThread(String url, int thread_id);

    /**
     * 更新线程下载进度
     *
     * @param url       线程地址
     * @param thread_id 线程id
     * @param finished  线程进度
     */
    void updateThread(String url, int thread_id, int finished);

    /**
     * 查询文件的线程信息
     *
     * @param url 线程地址
     * @return 线程信息
     */
    List<ThreadInfo> getThread(String url);

    /**
     * 线程信息是否存在
     *
     * @param url       线程地址
     * @param thread_id 线程id
     * @return
     */
    boolean isExists(String url, int thread_id);


}
