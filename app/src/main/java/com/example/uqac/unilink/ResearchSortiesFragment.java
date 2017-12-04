package com.example.uqac.unilink;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import static com.example.uqac.unilink.CustomAdapter.SORTIE;


/**
 * Created by Lorane on 02/12/2017.
 */

public class ResearchSortiesFragment extends GeneralFragmentDateTime {

    private static final String DIALOG_DATE = "sortie.DateDialog";
    private EditText datePickerAlertDialog;
    private EditText timePickerAlertDialogMin;
    private EditText timePickerAlertDialogMax;


    public boolean ok;
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

        datePickerAlertDialog = (EditText) view.findViewById(R.id.alert_dialog_date_picker);
        timePickerAlertDialogMin = (EditText) view.findViewById(R.id.alert_dialog_time_picker_min);
        timePickerAlertDialogMax = (EditText) view.findViewById(R.id.alert_dialog_time_picker_max);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        FloatingActionButton fabCancel = (FloatingActionButton) view.findViewById(R.id.fabCancel);

        datePickerAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onDatePicker();
            }
        });
        timePickerAlertDialogMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onTimePicker(timePickerAlertDialogMin);
            }
        });
        timePickerAlertDialogMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onTimePicker(timePickerAlertDialogMax);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {launchResearch();}
        });
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCancel();
            }
        });

        return view;
    }

    public void launchResearch(){

        //TODO
        // lancer la recherche de links selon les critères de l'utilisateur puis remplir newDataset et newDatasetTypes avec les résultats

        //String[] newDataset = new String[] {"NewSortie1", "NewSortie2", "NewSortie3", "NewSortie4", "NewSortie5", "NewSortie6"};
        //int[] newDatasetTypes = new int[]{SORTIE, SORTIE, SORTIE, SORTIE, SORTIE, SORTIE} ;


// Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();



// Attach a listener to read the data at our posts reference
        ref.child("sortie").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                final GeneralStructure[] newDataset = new GeneralStructure[(int) Math.min(dataSnapshot.getChildrenCount(),6)];
                final int[] newDatasetTypes = new int[(int) Math.min(dataSnapshot.getChildrenCount(),6)];
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                    SortieStructure sortie = eventSnapshot.getValue(SortieStructure.class);
                    if(sortie.date.equals(datePickerAlertDialog.getText().toString())) {
                        String[] time = sortie.heure.split ( "[ \\\\:]" );

                        int hour = Integer.parseInt ( time[0].trim() );
                        int min = Integer.parseInt ( time[1].trim() );
                        boolean am = time[2].startsWith("AM");
                        String[] time2 = timePickerAlertDialogMax.getText().toString().split ( "[ \\\\:]" );
                        int hour2 = Integer.parseInt ( time2[0].trim() );
                        int min2 = Integer.parseInt ( time2[1].trim() );
                        boolean am2 = time2[2].startsWith("AM");
                        String[] time3 = timePickerAlertDialogMin.getText().toString().split ( "[ \\\\:]" );
                        int hour3 = Integer.parseInt ( time3[0].trim() );
                        int min3 = Integer.parseInt ( time3[1].trim() );
                        boolean am3 = time3[2].startsWith("AM");
                        if( ( (hour >= hour3) || ((hour == hour3) && (min >= min3)) ) && ( (hour <= hour2) || ((hour == hour2) && (min <= min2)) ) ) {
                            newDataset[i] = sortie;
                            newDatasetTypes[i] = SORTIE;
                        }
                    }
                    i++;
                }
                int taille = 0;
                for(i = 0; i < newDataset.length; i++){
                        if(newDataset[i] != null)
                            taille++;
                }

                GeneralStructure[] dataset = new GeneralStructure[taille];
                int[] datasetTypes = new int[taille];

                for(i=0; i<taille;i++)
                    dataset[i] = newDataset[i];

                for(i=0; i<taille;i++)
                    datasetTypes[i] = newDatasetTypes[i];

                ((MainActivity) getActivity()).onSortieLaunch(dataset, datasetTypes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void launchCancel(){((MainActivity)getActivity()).onSortieAll();}

    @Override
    public void onFinishDialog(Date date) {
        datePickerAlertDialog.setText(super.formatDate(date));
    }

    @Override
    public void onFinishDialog(String time, EditText timePickerAlertDialog) {
        timePickerAlertDialog.setText(time);
    }

    public void onDatePicker(){
        DatePickerFragment dialog = new DatePickerFragment();
        dialog.fragment = this;
        dialog.show(getFragmentManager(), DIALOG_DATE);
    }

    @Override
    public void onTimePicker(EditText timePickerAlertDialog){
        TimePickerFragment dialog = new TimePickerFragment();
        dialog.fragment = this;
        dialog.timePickerAlertDialog = timePickerAlertDialog;
        dialog.show(getFragmentManager(), "TimePickerFragment");
    }

}
