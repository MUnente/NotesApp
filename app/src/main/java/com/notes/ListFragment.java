package com.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] city = { "Mumbai", "São Paulo", "Los Angels", "Sorocaba", "Capivari", "Barcelona", "Kyoto", "Tokyo", "Okinawa", "Roma", "Munich", "New York", "Toronto", "Amsterdam", "London", "Blumenau", "Salvador", "Cairo", "Nuuk", "Bergen", "Oslo" };

        ListView listView = (ListView) view.findViewById(R.id.lstNotes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, city);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            Toast.makeText(getActivity(), "Clicado", Toast.LENGTH_SHORT).show();
        }
    }
}