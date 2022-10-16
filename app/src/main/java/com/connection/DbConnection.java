package com.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbConnection {
    public SQLiteDatabase connect(Context context) {
        try {
            SQLiteDatabase db = context.openOrCreateDatabase("db_notes", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS note (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, date DATETIME, description VARCHAR)");
            return db;
        }
        catch (Exception e) {
            System.out.println("Error to connect: " + e.getMessage());
            return null;
        }
    }

    public void disconnect(SQLiteDatabase db) {
        db.close();
    }
}
