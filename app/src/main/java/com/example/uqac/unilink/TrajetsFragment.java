package com.example.uqac.unilink;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static com.example.uqac.unilink.CustomAdapter.TABLE;
import static com.example.uqac.unilink.CustomAdapter.TRAJET;

/**
 * Created by Lorane on 01/12/2017.
 */

public class TrajetsFragment extends GeneralFragment {

    private List<GeneralStructure> mDataset = new ArrayList<>();

    public TrajetsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_trajets, container, false);
        final Context context = view.getContext();

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        CustomAdapter mAdapter = new CustomAdapter(this, mDataset);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity)getActivity()).onAccueil();
        return true;
    }
}
