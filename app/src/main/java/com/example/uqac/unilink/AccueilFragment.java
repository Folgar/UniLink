package com.example.uqac.unilink;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lorane on 01/12/2017.
 */

public class AccueilFragment extends GeneralFragment {


    private List<GeneralStructure> mDataset;
    private CustomAdapter mAdapter;

    public AccueilFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.fragment_accueil, container, false);
        final Context context = view.getContext();

        mDataset = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("table");

        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("tester: ", "passe dedans");

                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                    TableStructure table = eventSnapshot.getValue(TableStructure.class);

                    mDataset.add(table);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref.removeEventListener(listener);
        ref.addListenerForSingleValueEvent(listener);

        ref = database.getReference().child("sortie");
        ValueEventListener listener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("tester: ", "passe dedans");

                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                    SortieStructure sortie = eventSnapshot.getValue(SortieStructure.class);

                    mDataset.add(sortie);
                }

                mAdapter = new CustomAdapter(AccueilFragment.this, mDataset);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref.addListenerForSingleValueEvent(listener2);

        ref.removeEventListener(listener2);

        return view;
    }
}
