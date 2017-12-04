package com.example.uqac.unilink;

import android.content.Context;
import android.os.Bundle;
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

public class LinksFragment extends GeneralFragment {

    GeneralStructure[] mDataset = {new TableStructure("10/12/17", "12:30","UQAC","test1","5"),
            new SortieStructure("12/12/17", "12:30","UQAC","test2","10")};
    int[] mDatasetTypes = {TABLE, SORTIE}; //view types

    public LinksFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_links, container, false);
        final Context context = view.getContext();

        //TODO
        //lancer requête firebase pour récupérer les links de l'utilisateur et les stocker dans mDataset et mDatasetTypes

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        CustomAdapter mAdapter = new CustomAdapter(this, mDataset, mDatasetTypes);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity)getActivity()).onAccueil();
        return true;
    }
}
