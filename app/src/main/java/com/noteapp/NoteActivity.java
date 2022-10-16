package com.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.beans.Note;
import com.dao.NoteDAO;

public class NoteActivity extends AppCompatActivity {

    private void loadData(Integer id) {
        EditText txtTitle = findViewById(R.id.txtTitle);
        EditText txtDescription = findViewById(R.id.txtDescription);

        Note note;
        NoteDAO noteDAO = new NoteDAO(this);

        try {
            note = noteDAO.selectNotes(id).get(0);

            txtTitle.setText(note.getTitle());
            txtDescription.setText(note.getDescription());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Não foi possível carregar os dados. Por favor, contate o suporte.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        int noteId;

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);

        if (noteId > 0) {
            loadData(noteId);
        }
    }
}