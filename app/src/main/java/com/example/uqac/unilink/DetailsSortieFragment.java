package com.example.uqac.unilink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Lorane on 03/12/2017.
 */

public class DetailsSortieFragment extends GeneralFragment {

    private SortieStructure sortieStructure;
    private GeneralFragment previousFragment;

    public DetailsSortieFragment(){}

    public static DetailsSortieFragment newInstance(SortieStructure sortie, GeneralFragment previousFragment){
        DetailsSortieFragment fragment = new DetailsSortieFragment();
        fragment.setDatas(sortie, previousFragment);
        return fragment;
    }

    public void setDatas(SortieStructure sortieStructure, GeneralFragment previousFragment){
        this.sortieStructure = sortieStructure;
        this.previousFragment = previousFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_details_sortie, container, false);

        TextView heure = (TextView) view.findViewById(R.id.heure);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView lieu = (TextView) view.findViewById(R.id.lieu);
        TextView description = (TextView) view.findViewById(R.id.description);
        Button rejoindre = (Button) view.findViewById(R.id.rejoindre);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, sortieStructure.Participants);

// Drop down layout style - list view
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner _EmpSpinner ;
        _EmpSpinner = (Spinner) view.findViewById(R.id.participants);

// attaching data adapter to spinner
        _EmpSpinner.setAdapter(dataAdapter);

        heure.setText(sortieStructure.heure);
        date.setText(sortieStructure.date);
        lieu.setText(sortieStructure.lieu);
        description.setText(sortieStructure.description);
//        participants.setText("Insérer ici la liste des participants");

        for(int i = 0 ; i < sortieStructure.Participants.size(); i++)
        {
            if(sortieStructure.Participants.get(i).equals(User.getInstance().getUser().getDisplayName()))
            {
                rejoindre.setVisibility(View.GONE);
            }
        }

        rejoindre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = database.child("sortie").child(sortieStructure.linkId);
                try {
                    List<String> participants = sortieStructure.Participants;
                    participants.add(User.getInstance().getUser().getDisplayName());
                    ref.child("Participants").setValue(participants);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    /**
     * Back pressed send from activity.
     *
     * @return if event is consumed, it will return true.
     */
    @Override
    public boolean onBackPressed() {
        if(previousFragment instanceof SortiesFragment)
            ((MainActivity)getActivity()).onSortieAll();
        else if(previousFragment instanceof LinksFragment)
            ((MainActivity)getActivity()).onLink();
        else
            ((MainActivity)getActivity()).onAccueil();
        return true;
    }
}
