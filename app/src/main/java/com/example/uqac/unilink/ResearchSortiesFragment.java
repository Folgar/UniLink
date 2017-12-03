package com.example.uqac.unilink;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.example.uqac.unilink.CustomAdapter.SORTIE;

/**
 * Created by Lorane on 02/12/2017.
 */

public class ResearchSortiesFragment extends Fragment {

    private Button researchButton;

    public ResearchSortiesFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_research_sorties, container, false);

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
        String[] newDataset = new String[] {"NewSortie1", "NewSortie2", "NewSortie3", "NewSortie4", "NewSortie5", "NewSortie6"};
        int[] newDatasetTypes = new int[]{SORTIE, SORTIE, SORTIE, SORTIE, SORTIE, SORTIE} ;

        ((MainActivity)getActivity()).onSortieLaunch(newDataset,newDatasetTypes);
    }
}
