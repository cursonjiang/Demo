package com.curson.downloaddemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 * Created by Curson on 15/4/13.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download.db";
    private static final int VERSON = 1;

    /**
     * 创建表
     */
    private static final String SQL_CREATE =
            "create table thread_info(" +
                    "_id integer primary key autoincrement," +
                    "thread_id integer," +
                    "url text," +
                    "start integer," +
                    "end integer," +
                    "finish integer";

    /**
     * 删除表
     */
    private static final String SQL_DROP = "drop table if exists thread_info";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
