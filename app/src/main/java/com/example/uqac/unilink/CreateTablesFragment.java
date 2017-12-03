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

public class CreateTablesFragment extends Fragment {

    public CreateTablesFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_tables, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCreation();
            }
        });

        return view;
    }

    public void launchCreation(){

        //TODO
        // créer le link selon les critères de l'utilisateur puis relancer TableFragment avec les links mis à jour

        String[] mDatasetTables = {"Table1", "Table2", "Table3", "Table4", "Table5", "Table6"};
        int mDatasetTypesTables[] = {TABLE, TABLE, TABLE, TABLE, TABLE, TABLE}; //view types

        ((MainActivity)getActivity()).onTableLaunch(mDatasetTables,mDatasetTypesTables);
    }

}
