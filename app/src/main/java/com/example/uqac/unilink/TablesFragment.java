package com.example.uqac.unilink;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

/**
 * Created by Lorane on 01/12/2017.
 */

public class TablesFragment extends Fragment {

    private ListView list;
    private Button button;


    public TablesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tables, container, false);
        button = (Button) view.findViewById(R.id.rechercher_button);
        list = (ListView) view.findViewById(R.id.list);
        final List<String> links = new ArrayList<String>();

        final ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_dropdown_item_1line, links);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("table").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                links.clear();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    TableOuverteStructure table = noteDataSnapshot.getValue(TableOuverteStructure.class);
                    links.add(0,"Lieu : " + table.lieu + "  Date : " + table.date+" "+table.heure);
                }
                // Pour update la list view (ne marche pas sans)
                adapter.add("");
                adapter.remove("");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        list.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateTableActivity.class));
            }
        });

        return view;
    }
}
