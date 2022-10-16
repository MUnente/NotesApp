package com.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beans.Note;
import com.dao.NoteDAO;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    // Global attributes
    private EditText txtTitle, txtDescription;
    private Button btnSave;

    private Note note = new Note();

    private int noteId;

    // Procedures
    private void loadData(Integer id) {
        NoteDAO noteDAO = new NoteDAO(this);

        try {
            this.note = noteDAO.selectNotes(id).get(0);

            this.txtTitle.setText(this.note.getTitle());
            this.txtDescription.setText(this.note.getDescription());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Não foi possível carregar os dados. Por favor, contate o suporte.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    // Event Listeners
    private void OnClickListener(View view) {
        if (this.txtTitle.getText().toString().trim().equalsIgnoreCase("") && this.txtDescription.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Não houve nenhum registro cadastrado/modificado.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            try {
                NoteDAO noteDAO = new NoteDAO(this);

                this.note.setTitle(this.txtTitle.getText().toString());
                this.note.setDescription(this.txtDescription.getText().toString());
                this.note.setDate(new Date());

                if (this.noteId > -1) {
                    noteDAO.updateNote(this.note);
                }
                else {
                    noteDAO.insertNote(this.note);
                }

                Snackbar.make(view, "Nota salva com sucesso!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                finish();
            }
            catch (Exception ex) {
                Toast.makeText(this, "Não foi possível concluir essa ação. Por favor, contate o suporte.", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }
    }

    // Main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        this.txtTitle = findViewById(R.id.txtTitle);
        this.txtDescription = findViewById(R.id.txtDescription);
        this.btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        this.noteId = intent.getIntExtra("noteId",-1);

        System.out.println(this.noteId);

        if (this.noteId > -1) {
            loadData(this.noteId);
        }

        this.btnSave.setOnClickListener(this::OnClickListener);
    }
}