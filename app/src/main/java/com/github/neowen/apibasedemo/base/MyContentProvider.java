package com.github.neowen.apibasedemo.base;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @date on 2018/11/28
 * @Author Winson
 */
public class MyContentProvider extends ContentProvider {

    public static final String URI = "com.winson.demo";
    public static final String DOMAIN = "domain";
    public static final String INSERT = "insert";
    public static final String QUERY = "query";
    public static final String DELETE = "delete";
    public static final String UPDATE = "update";
    public static final String DOMAIN_URI = "content://com.winson.demo/domain";
    public static final String INSERT_URI = "content://com.winson.demo/insert";
    public static final String QUERY_URI = "content://com.winson.demo/query";
    public static final String DELETE_URI = "content://com.winson.demo/delete";
    public static final String UPDATE_URI = "content://com.winson.demo/update";
    public static final int TYPE_DOMAIN = 1000;
    public static final int TYPE_INSERT = 1001;
    public static final int TYPE_QUERY = 1002;
    public static final int TYPE_UPDATE = 1003;
    public static final int TYPE_DELETE = 1004;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(URI, DOMAIN, TYPE_DOMAIN);
        uriMatcher.addURI(URI, INSERT, TYPE_INSERT);
        uriMatcher.addURI(URI, QUERY, TYPE_QUERY);
        uriMatcher.addURI(URI, DELETE, TYPE_DELETE);
        uriMatcher.addURI(URI, UPDATE, TYPE_UPDATE);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int code = uriMatcher.match(uri);
        Log.d("TAG", "query ---> ");
        if (code == TYPE_DOMAIN) {
            Log.d("TAG", "query ---> match");
            MyDbHelper myDbHelper = new MyDbHelper(getContext());
            return myDbHelper.getWritableDatabase().query(MyDbHelper.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        }
        Log.d("TAG", "query ---> match not ");
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int code = uriMatcher.match(uri);
        if (code == TYPE_DOMAIN) {
            Log.d("TAG", "need insert ");
            MyDbHelper myDbHelper = new MyDbHelper(getContext());
            myDbHelper.getWritableDatabase().insert(MyDbHelper.TABLE_NAME, null, values);
            Log.d("TAG", "need insert success!");
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        if (code == TYPE_DOMAIN) {
            MyDbHelper myDbHelper = new MyDbHelper(getContext());
            myDbHelper.getWritableDatabase().delete(MyDbHelper.TABLE_NAME, null, null);
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        if (code == TYPE_DOMAIN) {
            MyDbHelper myDbHelper = new MyDbHelper(getContext());
            myDbHelper.getWritableDatabase().update(MyDbHelper.TABLE_NAME, values, null, null);
        }
        return 0;
    }

}
