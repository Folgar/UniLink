package com.example.uqac.unilink;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
            public void onClick(View view) {
                launchResearch();
            }
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
        String[] newDataset = new String[] {};
        int[] newDatasetTypes = new int[]{} ;

        ((MainActivity)getActivity()).onSortieLaunch(newDataset,newDatasetTypes);
    }

    public void launchCancel(){
        String[] mDatasetTables = {"Sortie1", "Sortie2", "Sortie3", "Sortie4", "Sortie5", "Sortie6"};
        int mDatasetTypesTables[] = {SORTIE, SORTIE, SORTIE, SORTIE, SORTIE, SORTIE}; //view types

        ((MainActivity)getActivity()).onSortieLaunch(mDatasetTables,mDatasetTypesTables);
    }

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
