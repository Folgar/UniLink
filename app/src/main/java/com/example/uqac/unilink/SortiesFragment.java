package com.example.uqac.unilink;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lorane on 01/12/2017.
 */

public class SortiesFragment extends GeneralFragment {

    private GeneralStructure[] mDataset;
    private int[] mDatasetTypes; //view types

    public SortiesFragment(){}

    public static SortiesFragment newInstance(GeneralStructure[] mDataset, int[] mDatasetTypes){
        SortiesFragment fragment = new SortiesFragment();
        fragment.setDatas(mDataset, mDatasetTypes);
        return fragment;
    }

    public void setDatas(GeneralStructure[] mDataset, int[] mDatasetTypes){
        this.mDataset = mDataset;
        this.mDatasetTypes = mDatasetTypes;
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

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        CustomAdapter mAdapter = new CustomAdapter(this, mDataset, mDatasetTypes);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).onResearchSortieLaunch();
            }
        });

        return view;
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity)getActivity()).onAccueil();
        return true;
    }
}
