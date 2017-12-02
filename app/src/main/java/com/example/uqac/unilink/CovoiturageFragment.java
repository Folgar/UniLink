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
import static com.example.uqac.unilink.CustomAdapter.TABLE;

/**
 * Created by Lorane on 01/12/2017.
 */

public class CovoiturageFragment extends Fragment {

    private RecyclerView mRecyclerView ;
    private CustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mDataset = {"Covoiturage1", "Covoiturage2", "Covoiturage3", "Covoiturage4", "Covoiturage5", "Covoiturage6"};
    private int mDatasetTypes[] = {COVOITURAGE, COVOITURAGE, COVOITURAGE, COVOITURAGE, COVOITURAGE, COVOITURAGE}; //view types

    public CovoiturageFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_covoiturage, container, false);
        final Context context = view.getContext();

        mRecyclerView  = (RecyclerView) view.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomAdapter(mDataset,mDatasetTypes);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
