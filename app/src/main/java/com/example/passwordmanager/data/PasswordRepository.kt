package com.example.passwordmanager.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PasswordRepository(context: Context) : SQLiteOpenHelper(context, "passwords.db", null, 2) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE passwords (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "service TEXT, " +
                    "login TEXT, " +
                    "password TEXT, " +
                    "note TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS passwords")
        onCreate(db)
    }

    fun addPassword(entry: PasswordEntry) {
        writableDatabase.execSQL(
            "INSERT INTO passwords (service, login, password, note) VALUES (?, ?, ?, ?)",
            arrayOf(entry.service, entry.login, entry.password, entry.note)
        )
    }

    fun deletePassword(entry: PasswordEntry) {
        writableDatabase.execSQL("DELETE FROM passwords WHERE id = ?",
            arrayOf(entry.id))
    }

    fun updatePassword(entry: PasswordEntry) {
        writableDatabase.execSQL(
            "UPDATE passwords SET service = ?, login = ?, password = ?, note = ? WHERE id = ?",
            arrayOf(entry.service, entry.login, entry.password, entry.note, entry.id)
        )
    }

    fun getAllPasswords(): List<PasswordEntry> {
        val cursor = readableDatabase.rawQuery("SELECT id, service, login, password, note FROM passwords", null)
        val results = mutableListOf<PasswordEntry>()
        while (cursor.moveToNext()) {
            results.add(
                PasswordEntry(
                    id = cursor.getInt(0),
                    service = cursor.getString(1),
                    login = cursor.getString(2),
                    password = cursor.getString(3),
                    note = cursor.getString(4)
                )
            )
        }
        cursor.close()
        return results
    }
}