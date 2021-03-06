package com.curson.downloaddemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.curson.downloaddemo.entities.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据访问接口实现
 * Created by Curson on 15/4/13.
 */
public class ThreadDAOImpl implements ThreadDAO {

    private SQLiteDatabase db;

    private DBHelper mDBHelper = null;

    public ThreadDAOImpl(Context context) {
        mDBHelper = new DBHelper(context);
    }

    @Override
    public void insertThread(ThreadInfo threadInfo) {
        db = mDBHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id, url, start, end, finished) values(?, ?, ?, ?, ?)",
                new Object[]{threadInfo.getId(), threadInfo.getUrl(), threadInfo.getStart(), threadInfo.getEnd(), threadInfo.getFinished()});
        db.close();
    }

    @Override
    public void deleteThread(String url, int thread_id) {
        db = mDBHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ? and thread_id = ?", new Object[]{url, thread_id});
        db.close();
    }

    @Override
    public void updateThread(String url, int thread_id, int finished) {
        db = mDBHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished = ? where url = ? and thread_id = ? ",
                new Object[]{finished, url, thread_id});
        db.close();
    }

    @Override
    public List<ThreadInfo> getThread(String url) {
        db = mDBHelper.getWritableDatabase();
        List<ThreadInfo> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{url});
        while (cursor.moveToNext()) {
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, String.valueOf(thread_id)});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }
}
