package com.github.neowen.apibasedemo.base;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @date on 2018/11/28
 * @Author Winson
 */
public class MyDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 4;
    public static final String TABLE_NAME = "server";
    public static final String DOMAIN = "domain";
    private static final String CREATE_SERVER_TABLE = "create table " + TABLE_NAME + " (" + DOMAIN + " text)";

    public MyDbHelper(@Nullable Context context) {
        super(context, "server", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table " + TABLE_NAME);
        db.execSQL(CREATE_SERVER_TABLE);
        Log.d("TAG", "create db success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
