package com.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beans.Note;

import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {

    final private Context context;
    private ArrayList<Note> notes;
    private ArrayList<Integer> ids = new ArrayList<>();

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return this.notes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        }

        Note note = (Note) getItem(position);

        TextView title = (TextView) convertView.findViewById(android.R.id.text1);
        title.setText(note.getTitle());

        ids.add(note.getId());

        return convertView;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public ArrayList<Integer> getArrayIds() {
        return this.ids;
    }
}
