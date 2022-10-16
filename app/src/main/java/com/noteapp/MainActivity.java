package com.noteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beans.Note;
import com.custom.NoteAdapter;
import com.dao.NoteDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lstNotes;
    private FloatingActionButton fabAddNote;

    private ArrayList<Integer> note_ids;

    // Procedures
    private void fillList() {
        ArrayList<Note> notes;
        NoteAdapter adapter = new NoteAdapter(MainActivity.this, null);
        NoteDAO noteDAO = new NoteDAO(this);

        notes = noteDAO.selectNotes(null);

        adapter.setNotes(notes);
        this.lstNotes.setAdapter(adapter);

        this.note_ids = adapter.getArrayIds();
    }
    
    private void deleteItem(int id) {
        NoteDAO noteDAO = new NoteDAO(this);
        noteDAO.deleteNote(id);
    }

    // Event Listeners
    private void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intentAddNewNote = new Intent(this, NoteActivity.class);
        intentAddNewNote.putExtra("noteId", note_ids.get(position));
        startActivity(intentAddNewNote);
    }

    private boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle(R.string.title_delete_note);
        alertBuilder.setIcon(android.R.drawable.ic_menu_delete);
        alertBuilder.setMessage(R.string.sure_about_that);

        alertBuilder.setPositiveButton(R.string.confirm_delete_note, (dialogInterface, i) -> {
            deleteItem(note_ids.get(position));
            Snackbar.make(view, R.string.sucessfully_note_deletion, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            fillList();
        });

        alertBuilder.setNegativeButton(R.string.cancel_delete_note, (dialogInterface, i) -> {
        });

        alertBuilder.show();
        return true;
    }

    private void onClick(View view) {
        Intent intentAddNewNote = new Intent(this, NoteActivity.class);
        intentAddNewNote.putExtra("noteId", -1);
        startActivity(intentAddNewNote);
    }

    // Main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lstNotes = findViewById(R.id.lstNotes);
        this.fabAddNote = findViewById(R.id.fabAddNote);

        fillList();

        this.lstNotes.setOnItemClickListener(this::onItemClick);
        this.lstNotes.setOnItemLongClickListener(this::onItemLongClick);
        this.fabAddNote.setOnClickListener(this::onClick);
    }
}