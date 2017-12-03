package com.example.uqac.unilink;

import android.os.Bundle;
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

    private Button researchButton;

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

        researchButton = (Button) view.findViewById(R.id.researchButton);
        researchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchResearch();
            }
        });
        return view;
    }

    public void launchResearch(){
        String[] newDataset = new String[] {"NewTable1", "NewTable2", "NewTable3", "NewTable4", "NewTable5", "NewTable6"};
        int[] newDatasetTypes = new int[]{TABLE, TABLE, TABLE, TABLE, TABLE, TABLE} ;

        ((MainActivity)getActivity()).onTableLaunch(newDataset,newDatasetTypes);
    }
}
