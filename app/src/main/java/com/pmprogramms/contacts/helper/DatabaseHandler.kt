package com.pmprogramms.contacts.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pmprogramms.contacts.model.Message

class DatabaseHandler(private var context: Context?) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "fastMessages"
        private val TABLE_MESSAGES = "messages"
        private val KEY_ID = "id"
        private val KEY_VALUE = "value"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE $TABLE_MESSAGES ($KEY_ID INTEGER PRIMARY KEY, $KEY_VALUE TEXT)")

        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MESSAGES");
        onCreate(db);
    }

    fun addMessage(value: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_VALUE, value)

        db.insert(TABLE_MESSAGES, null, contentValues)
    }

    fun getMessages() : ArrayList<Message> {
        val listMessages = ArrayList<Message>()
        val query = "SELECT * FROM $TABLE_MESSAGES;"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do  {
                listMessages.add(Message(cursor.getInt(0), cursor.getString(1)))
            }while (cursor.moveToNext())
        }

        cursor.close()
        return listMessages
    }
}
