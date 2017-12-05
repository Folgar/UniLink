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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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



// Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();


        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                //final GeneralStructure[] newDataset = new GeneralStructure[(int) Math.min(dataSnapshot.getChildrenCount(),6)];
                //final int[] newDatasetTypes = new int[(int) Math.min(dataSnapshot.getChildrenCount(),6)];
                final List<GeneralStructure> newDataset = new ArrayList<>();
                final List<Integer> newDatasetTypes = new ArrayList<>();

                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                    SortieStructure sortie = eventSnapshot.getValue(SortieStructure.class);

                    if ((!datePickerAlertDialog.getText().toString().equals("")) && (!timePickerAlertDialogMin.getText().toString().equals("")) && (!timePickerAlertDialogMax.getText().toString().equals(""))) {
                        if (sortie.date.equals(datePickerAlertDialog.getText().toString())) {
                            String[] time = sortie.heure.split("[ \\\\:]");

                            int hour = Integer.parseInt(time[0].trim());
                            int min = Integer.parseInt(time[1].trim());
                            boolean am = time[2].startsWith("AM");
                            String[] time2 = timePickerAlertDialogMax.getText().toString().split("[ \\\\:]");
                            int hour2 = Integer.parseInt(time2[0].trim());
                            int min2 = Integer.parseInt(time2[1].trim());
                            boolean am2 = time2[2].startsWith("AM");
                            String[] time3 = timePickerAlertDialogMin.getText().toString().split("[ \\\\:]");
                            int hour3 = Integer.parseInt(time3[0].trim());
                            int min3 = Integer.parseInt(time3[1].trim());
                            boolean am3 = time3[2].startsWith("AM");

                            if (((am && am3) || (!am && !am2)) && (((hour >= hour3) || ((hour == hour3) && (min >= min3))) && ((hour <= hour2) || ((hour == hour2) && (min <= min2))))) {
                                newDataset.add(sortie);
                                newDatasetTypes.add(SORTIE);
                            }

                        }
                    } else if ((!datePickerAlertDialog.getText().toString().equals("")) && (!timePickerAlertDialogMin.getText().toString().equals(""))) {
                        if (sortie.date.equals(datePickerAlertDialog.getText().toString())) {
                            String[] time = sortie.heure.split("[ \\\\:]");

                            int hour = Integer.parseInt(time[0].trim());
                            int min = Integer.parseInt(time[1].trim());
                            boolean am = time[2].startsWith("AM");

                            String[] time3 = timePickerAlertDialogMin.getText().toString().split("[ \\\\:]");
                            int hour3 = Integer.parseInt(time3[0].trim());
                            int min3 = Integer.parseInt(time3[1].trim());
                            boolean am3 = time3[2].startsWith("AM");

                            if (!(am && !am3) && ((hour >= hour3) || ((hour == hour3) && (min >= min3)))) {
                                newDataset.add(sortie);
                                newDatasetTypes.add(SORTIE);
                            }

                        }
                    } else if ((!datePickerAlertDialog.getText().toString().equals("")) && (!timePickerAlertDialogMax.getText().toString().equals(""))) {
                        if (sortie.date.equals(datePickerAlertDialog.getText().toString())) {
                            String[] time = sortie.heure.split("[ \\\\:]");

                            int hour = Integer.parseInt(time[0].trim());
                            int min = Integer.parseInt(time[1].trim());
                            boolean am = time[2].startsWith("AM");
                            String[] time2 = timePickerAlertDialogMax.getText().toString().split("[ \\\\:]");
                            int hour2 = Integer.parseInt(time2[0].trim());
                            int min2 = Integer.parseInt(time2[1].trim());
                            boolean am2 = time2[2].startsWith("AM");

                            if (!(!am && am2) && ((hour <= hour2) || ((hour == hour2) && (min <= min2)))) {
                                newDataset.add(sortie);
                                newDatasetTypes.add(SORTIE);
                            }

                        }
                    } else if ((!timePickerAlertDialogMin.getText().toString().equals("")) && (!timePickerAlertDialogMax.getText().toString().equals(""))) {
                        String[] time = sortie.heure.split("[ \\\\:]");

                        int hour = Integer.parseInt(time[0].trim());
                        int min = Integer.parseInt(time[1].trim());
                        boolean am = time[2].startsWith("AM");
                        String[] time2 = timePickerAlertDialogMax.getText().toString().split("[ \\\\:]");
                        int hour2 = Integer.parseInt(time2[0].trim());
                        int min2 = Integer.parseInt(time2[1].trim());
                        boolean am2 = time2[2].startsWith("AM");
                        String[] time3 = timePickerAlertDialogMin.getText().toString().split("[ \\\\:]");
                        int hour3 = Integer.parseInt(time3[0].trim());
                        int min3 = Integer.parseInt(time3[1].trim());
                        boolean am3 = time3[2].startsWith("AM");

                        if (((am && am3) || (!am && !am2)) && (((hour >= hour3) || ((hour == hour3) && (min >= min3))) && ((hour <= hour2) || ((hour == hour2) && (min <= min2))))) {
                            newDataset.add(sortie);
                            newDatasetTypes.add(SORTIE);
                        }


                    } else if ((!datePickerAlertDialog.getText().toString().equals(""))) {
                        if (sortie.date.equals(datePickerAlertDialog.getText().toString())) {
                            newDataset.add(sortie);
                            newDatasetTypes.add(SORTIE);
                        }
                    } else if ((!timePickerAlertDialogMin.getText().toString().equals(""))) {

                        String[] time = sortie.heure.split("[ \\\\:]");

                        int hour = Integer.parseInt(time[0].trim());
                        int min = Integer.parseInt(time[1].trim());
                        boolean am = time[2].startsWith("AM");

                        String[] time3 = timePickerAlertDialogMin.getText().toString().split("[ \\\\:]");
                        int hour3 = Integer.parseInt(time3[0].trim());
                        int min3 = Integer.parseInt(time3[1].trim());
                        boolean am3 = time3[2].startsWith("AM");

                        if (!(am && !am3) && ((hour >= hour3) || ((hour == hour3) && (min >= min3)))) {
                            newDataset.add(sortie);
                            newDatasetTypes.add(SORTIE);
                        }


                    } else if ((!timePickerAlertDialogMax.getText().toString().equals(""))) {
                        String[] time = sortie.heure.split("[ \\\\:]");

                        int hour = Integer.parseInt(time[0].trim());
                        int min = Integer.parseInt(time[1].trim());
                        boolean am = time[2].startsWith("AM");
                        String[] time2 = timePickerAlertDialogMax.getText().toString().split("[ \\\\:]");
                        int hour2 = Integer.parseInt(time2[0].trim());
                        int min2 = Integer.parseInt(time2[1].trim());
                        boolean am2 = time2[2].startsWith("AM");

                        if (!(!am && am2) && (((hour <= hour2 && !am) || (am && !am2)) || ((hour == hour2) && (min <= min2)))) {
                            newDataset.add(sortie);
                            newDatasetTypes.add(SORTIE);
                        }

                    }
                }
                ((MainActivity) getActivity()).onSortieLaunch(newDataset, newDatasetTypes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }


        };

// Attach a listener to read the data at our posts reference
        ref.child("sortie").addListenerForSingleValueEvent(listener);

        ref.child("sortie").removeEventListener(listener);

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
