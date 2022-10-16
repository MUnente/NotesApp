package com.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.connection.DbConnection;
import com.beans.Note;

public class NoteDAO {
    final private DbConnection dbconnection = new DbConnection();
    private SQLiteDatabase database;
    final private Context context;

    public NoteDAO(Context context) {
        this.context = context;
    }

    public ArrayList<Note> selectNotes(Integer id) {
        String query = "SELECT * FROM note";
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor;

        if (id != null) {
            query += " WHERE id = " + id;
        }

        query += " ORDER BY date DESC";

        System.out.println("MAMA MINHA PIKA");

        try {
            this.database = this.dbconnection.connect(this.context);

            cursor = this.database.rawQuery(query, null);

            while (cursor.moveToNext()) {
                Note note = new Note();

                System.out.println(cursor.getString(0));
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(cursor.getString(2)));
                note.setDescription(cursor.getString(3));

                notes.add(note);
            }

            cursor.close();
            this.dbconnection.disconnect(this.database);

            return notes;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void insertNote(Note note) {
        String query = "INSERT INTO note (title, date, description) VALUES (?, ?, ?)";

        System.out.println("FILHO DA PUTA");
        System.out.println(note.getTitle());

        try {
            this.database = this.dbconnection.connect(this.context);

            SQLiteStatement stmt = this.database.compileStatement(query);
            stmt.bindString(1, note.getTitle());
            stmt.bindString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(note.getDate()));
            stmt.bindString(3, note.getDescription());

            stmt.executeInsert();

            this.dbconnection.disconnect(this.database);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateNote(Note note) {
        String query = "UPDATE note SET title = ?, date = ?, description = ? WHERE id = ?";

        System.out.println("VAI TOMA NO CU");
        System.out.println(note.getTitle());

        try {
            this.database = this.dbconnection.connect(this.context);

            SQLiteStatement stmt = this.database.compileStatement(query);
            stmt.bindString(1, note.getTitle());
            stmt.bindString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(note.getDate()));
            stmt.bindString(3, note.getDescription());
            stmt.bindLong(4, note.getId());

            stmt.executeUpdateDelete();

            this.dbconnection.disconnect(this.database);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteNote(int id) {
        String query = "DELETE FROM note WHERE id = ?";
        System.out.println("ARROMBADO");
        System.out.println(id);

        try {
            this.database = this.dbconnection.connect(this.context);

            SQLiteStatement stmt = this.database.compileStatement(query);
            stmt.bindLong(1, id);
            stmt.executeUpdateDelete();

            this.dbconnection.disconnect(this.database);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
