package com.winson.apibasedemo.bean

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson

/**
 * @date 2019/11/7
 * @author winson-zhou
 */
class ParasiteDbHelper : SQLiteOpenHelper {

    companion object {
        private const val DB_NAME = "parasite_db"
        private const val DB_VERSION = 1

        private const val PARASITE_TABLE_NAME = "parasite"
        private const val COLUMN_ID = "id"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_CREATE_TIME = "create_time"

        private const val CREATE_TABLE =
            " CREATE TABLE IF NOT EXISTS $PARASITE_TABLE_NAME (" +
                    "$COLUMN_ID INTEGER primary key ," +
                    "$COLUMN_CONTENT TEXT," +
                    "$COLUMN_CREATE_TIME INTEGER) "

    }

    private val gson = Gson()

    constructor(context: Context)
            : super(context, DB_NAME, null, DB_VERSION)

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addRecord(bean: ParasiteBean) {
        val content = gson.toJson(bean)
        val createTime = System.currentTimeMillis()

        val db = writableDatabase
        val record = ContentValues()
        record.put(COLUMN_CONTENT, content)
        record.put(COLUMN_CREATE_TIME, createTime)
        db.insert(PARASITE_TABLE_NAME, null, record)
        db.close()
    }

    fun queryRecord(): ArrayList<ParasiteRecord> {
        val records = arrayListOf<ParasiteRecord>()
        val db = readableDatabase
        val cursor = db.query(
            PARASITE_TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null,
            "100"
        )
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT))
                val record = ParasiteRecord(id, content)
                records.add(record)
            } while (cursor.moveToNext())
        }
        db.close()
        return records
    }

    fun deleteRecord(id: Int) {
        val db = writableDatabase
        db.delete(PARASITE_TABLE_NAME, " $COLUMN_ID = ? ", arrayOf("$id"))
        db.close()
    }

}