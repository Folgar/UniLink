package com.example.uqac.unilink;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by Lorane on 03/12/2017.
 */

public class CreateSortiesFragment extends GeneralFragmentDateTime {

    private static final String DIALOG_DATE = "sortie.DateDialog";
    private EditText datePickerAlertDialog;
    private EditText timePickerAlertDialog;
    private EditText nombre;
    private EditText lieu;
    private EditText description;
    private DatabaseReference mRefSortie;
    private DatabaseReference mRefLink;
    private MapsActivity dialogFragment;


    public CreateSortiesFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_sorties, container, false);
        final Context context = view.getContext();
        final View inflate = View.inflate(context, R.layout.activity_maps, null);
        datePickerAlertDialog = (EditText) view.findViewById(R.id.alert_dialog_date_picker);
        timePickerAlertDialog = (EditText) view.findViewById(R.id.alert_dialog_time_picker);
        nombre = (EditText) view.findViewById(R.id.nombre_participants);
        lieu = (EditText) view.findViewById(R.id.lieu);
        description = (EditText) view.findViewById(R.id.description);
        FloatingActionButton fabOK = (FloatingActionButton) view.findViewById(R.id.fabOK);
        FloatingActionButton fabCancel = (FloatingActionButton) view.findViewById(R.id.fabCancel);
        mRefSortie = FirebaseDatabase.getInstance().getReference("sortie");
        mRefLink = FirebaseDatabase.getInstance().getReference("link");


        datePickerAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onDatePicker();
            }
        });

        timePickerAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onTimePicker(timePickerAlertDialog);
            }
        });

        lieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inflate.getParent()!=null)
                    ((ViewGroup)inflate.getParent()).removeView(inflate);

                final FragmentManager fm = getFragmentManager();
                dialogFragment = new MapsActivity();
                dialogFragment.context = getContext();
//                ViewGroup viewGroup = (ViewGroup) new View(context);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
// Get the layout inflater
                final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
// Inflate and set the layout for the dialog
// Pass null as the parent view because its going in the dialog
// layout
//                    dialogFragment.setContentView(R.layout.activity_maps);
//                dialogFragment.customAdapter=CustomAdapter.this;
                builder.setView(inflate);
                final AlertDialog ad = builder.create();
                ad.setTitle("Select starting point");
                ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            public void onClick(DialogInterface dialog, int which) {
//                                dialogFragment.finishAndRemoveTask();
//                                dialogFragment.end();

                                  ad.dismiss();
                            }
                        });
                ad.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lieu.setText(dialogFragment.address);
//                        dialogFragment.finishAndRemoveTask();
//                        dialogFragment.end();
                          ad.dismiss();


                    }
                });

                ad.show();

                SupportMapFragment mapFragment = (SupportMapFragment) fm
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(dialogFragment);



            }
        });

        fabOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchCreation();
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

    public void launchCreation(){

        if (datePickerAlertDialog.getText().toString().matches(""))
            Toast.makeText(getContext(), "Date manquante", Toast.LENGTH_SHORT).show();

        else if (timePickerAlertDialog.getText().toString().matches(""))
            Toast.makeText(getContext(), "Heure manquante", Toast.LENGTH_SHORT).show();

        else if (lieu.getText().toString().matches(""))
            Toast.makeText(getContext(), "Lieu manquant", Toast.LENGTH_SHORT).show();

        else if (nombre.getText().toString().matches(""))
            Toast.makeText(getContext(), "Nombre de participants maximum manquant", Toast.LENGTH_SHORT).show();

        else {
            SortieStructure sortie = new SortieStructure(datePickerAlertDialog.getText().toString(), timePickerAlertDialog.getText().toString(), lieu.getText().toString(), description.getText().toString(), nombre.getText().toString(),User.getInstance().getUser().getDisplayName(), User.getInstance().getUser().getDisplayName() );
            String linkId = mRefLink.push().getKey();
            mRefLink.child(linkId).setValue("Sortie");
            mRefSortie.child(linkId).setValue(sortie);
            Toast.makeText(getContext(), "Sortie enregistrée", Toast.LENGTH_SHORT).show();
            //finish();
            ((MainActivity)getActivity()).onSortieAll();
        }


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

    @Override
    public boolean onBackPressed() {
        ((MainActivity)getActivity()).onSortieAll();
        return true;
    }
}
