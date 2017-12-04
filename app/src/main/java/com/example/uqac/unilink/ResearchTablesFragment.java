package com.example.uqac.unilink;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.uqac.unilink.CustomAdapter.SORTIE;

/**
 * Created by Lorane on 02/12/2017.
 */

public class ResearchTablesFragment extends Fragment {

    private static final String DIALOG_DATE = "table.DateDialog";
    private EditText datePickerAlertDialog;
    private EditText timePickerAlertDialogMin;
    private EditText timePickerAlertDialogMax;

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

        datePickerAlertDialog = (EditText) view.findViewById(R.id.alert_dialog_date_picker);
        timePickerAlertDialogMin = (EditText) view.findViewById(R.id.alert_dialog_time_picker_min);
        timePickerAlertDialogMax = (EditText) view.findViewById(R.id.alert_dialog_time_picker_max);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        FloatingActionButton fabCancel = (FloatingActionButton) view.findViewById(R.id.fabCancel);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchResearch();
            }
        });

        return view;
    }

    public void launchResearch(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("table").orderByChild("date").equalTo(datePickerAlertDialog.getText().toString());

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int i=0;
                final GeneralStructure[] newDataset = new GeneralStructure[(int) Math.min(dataSnapshot.getChildrenCount(),6)];
                final int[] newDatasetTypes = new int[(int) Math.min(dataSnapshot.getChildrenCount(),6)];
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                    TableStructure sortie = eventSnapshot.getValue(TableStructure.class);
                    if(sortie.date.equals(datePickerAlertDialog)) {
                        if(sortie.date.equals(datePickerAlertDialog)) {
                            newDataset[i] = sortie;
                            newDatasetTypes[i] = SORTIE;
                        }
                    }
                    i++;
                }
                ((MainActivity) getActivity()).onTableLaunch(newDataset, newDatasetTypes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
