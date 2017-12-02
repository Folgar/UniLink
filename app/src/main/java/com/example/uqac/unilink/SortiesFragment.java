package com.example.uqac.unilink;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.uqac.unilink.CustomAdapter.SORTIE;
import static com.example.uqac.unilink.CustomAdapter.TABLE;

/**
 * Created by Lorane on 01/12/2017.
 */

public class SortiesFragment extends Fragment {

    private RecyclerView mRecyclerView ;
    private CustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mDataset = {"Sortie1", "Sortie2", "Sortie3", "Sortie4", "Sortie5", "Sortie6"};
    private int mDatasetTypes[] = {SORTIE, SORTIE, SORTIE, SORTIE, SORTIE, SORTIE}; //view types

    public SortiesFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_sorties, container, false);
        final Context context = view.getContext();

        mRecyclerView  = (RecyclerView) view.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomAdapter(mDataset,mDatasetTypes);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
