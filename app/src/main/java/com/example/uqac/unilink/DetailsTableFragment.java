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

public class DetailsTableFragment extends GeneralFragment{

    private TableStructure tableStructure;
    private GeneralFragment previousFragment;
    private Button rejoindre;

    public DetailsTableFragment(){}

    public static DetailsTableFragment newInstance(TableStructure table, GeneralFragment previousFragment){
        DetailsTableFragment fragment = new DetailsTableFragment();
        fragment.setDatas(table, previousFragment);
        return fragment;
    }

    public void setDatas(TableStructure tableStructure, GeneralFragment previousFragment){
        this.tableStructure = tableStructure;
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
        final View view = inflater.inflate(R.layout.fragment_details_table, container, false);

        TextView heure = (TextView) view.findViewById(R.id.heure);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView lieu = (TextView) view.findViewById(R.id.lieu);
        TextView description = (TextView) view.findViewById(R.id.description);
        rejoindre = (Button) view.findViewById(R.id.rejoindre);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, tableStructure.Participants);

// Drop down layout style - list view
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner _EmpSpinner ;
        _EmpSpinner = (Spinner) view.findViewById(R.id.participants);

// attaching data adapter to spinner
        _EmpSpinner.setAdapter(dataAdapter);

        heure.setText(tableStructure.heure);
        date.setText(tableStructure.date);
        lieu.setText(tableStructure.lieu);
        description.setText(tableStructure.description);
//        participants.setText("Insérer ici la liste des participants");

        for(int i = 0 ; i < tableStructure.Participants.size(); i++)
        {
            if(tableStructure.Participants.get(i).equals(User.getInstance().getUser().getDisplayName()))
            {
                rejoindre.setVisibility(View.GONE);
            }
        }



        rejoindre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference ref = database.child("table").child(tableStructure.linkId);
                    try {
                        List<String> participants = tableStructure.Participants;
                        participants.add(User.getInstance().getUser().getDisplayName());
                        ref.child("Participants").setValue(participants);
                        rejoindre.setVisibility(View.GONE);
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
        if(previousFragment instanceof TablesFragment)
            ((MainActivity)getActivity()).onTableAll();
        else if(previousFragment instanceof LinksFragment)
            ((MainActivity)getActivity()).onLink();
        else
            ((MainActivity)getActivity()).onAccueil();
        return true;
    }
}
