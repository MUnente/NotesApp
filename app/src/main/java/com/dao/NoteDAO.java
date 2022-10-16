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

        try {
            this.database = this.dbconnection.connect(this.context);

            cursor = this.database.rawQuery(query, null);

            while (cursor.moveToNext()) {
                Note note = new Note();

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

    public void insertNote() {
        // refazer método!
        String query = "INSERT INTO note (title, date, description) VALUES " +
                "('Teste 1', '2022-10-13 23:59:59', 'Este é o primeiro teste para fazer alguma coisa'), " +
                "('Teste 2', '2022-10-10 14:54:32', 'Foi bem no primeiro dia de trabalho'), " +
                "('Teste 3', '2021-07-23 15:00:11', 'Neste momento eu estava sem saber que horas eram, se eu ainda iria continuar bem ou não... Esse dia marcou a minha vida (se eu acertei o dia)');";

        try {
            this.database = this.dbconnection.connect(this.context);

            SQLiteStatement stmt = this.database.compileStatement(query);
            stmt.executeInsert();

            this.dbconnection.disconnect(this.database);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteNote(int id) {
        String query = "DELETE FROM note WHERE id = ?";

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
