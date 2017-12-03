package com.example.uqac.unilink;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.example.uqac.unilink.CustomAdapter.TABLE;

/**
 * Created by Lorane on 02/12/2017.
 */

public class ResearchTablesFragment extends Fragment {

    public ResearchTablesFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_research_tables, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchResearch();
            }
        });

        return view;
    }

    public void launchResearch(){

        //TODO Requête firebase
        GeneralStructure[] newDataset = new GeneralStructure[] {};
        int[] newDatasetTypes = new int[]{} ;

        ((MainActivity)getActivity()).onTableLaunch(newDataset,newDatasetTypes);
    }
}
