package com.example.uqac.unilink;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.uqac.unilink.CustomAdapter.COVOITURAGE;
import static com.example.uqac.unilink.CustomAdapter.SORTIE;
import static com.example.uqac.unilink.CustomAdapter.TABLE;
import static com.example.uqac.unilink.CustomAdapter.TRAJET;

/**
 * Created by Lorane on 01/12/2017.
 */

public class TablesFragment extends Fragment {

    private RecyclerView mRecyclerView ;
    private CustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mDataset = {"Table1", "Table2", "Table3", "Table4", "Table5", "Table6"};
    private int mDatasetTypes[] = {TABLE, TABLE, TABLE, TABLE, TABLE, TABLE}; //view types

    public TablesFragment(){

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
        final Context context = view.getContext();

        mRecyclerView  = (RecyclerView) view.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomAdapter(mDataset,mDatasetTypes);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
