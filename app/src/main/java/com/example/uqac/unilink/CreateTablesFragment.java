package com.example.uqac.unilink;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import static com.example.uqac.unilink.CustomAdapter.TABLE;

/**
 * Created by Lorane on 02/12/2017.
 */

public class CreateTablesFragment extends GeneralFragmentDateTime {

    private static final String DIALOG_DATE = "table.DateDialog";
    private EditText datePickerAlertDialog;
    private EditText timePickerAlertDialog;
    private EditText nombre;
    private EditText lieu;
    private EditText description;
    private DatabaseReference mRefTable;
    private DatabaseReference mRefLink;

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

        final Context context = view.getContext();

        datePickerAlertDialog = (EditText) view.findViewById(R.id.alert_dialog_date_picker);
        timePickerAlertDialog = (EditText) view.findViewById(R.id.alert_dialog_time_picker);
        nombre = (EditText) view.findViewById(R.id.nombre_participants);
        lieu = (EditText) view.findViewById(R.id.lieu);
        description = (EditText) view.findViewById(R.id.description);
        FloatingActionButton fabOK = (FloatingActionButton) view.findViewById(R.id.fabOK);
        FloatingActionButton fabCancel = (FloatingActionButton) view.findViewById(R.id.fabCancel);
        mRefTable = FirebaseDatabase.getInstance().getReference("table");
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
//                    MapsActivity map = new MapsActivity();
//                    FragmentManager fm = mParentFragment.getFragmentManager();
//                    MapsActivity dialogFragment = new MapsActivity ();
////                    dialogFragment.show(fm, "Sample Fragment");
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mParentFragment.getContext());
//// Get the layout inflater
//                    LayoutInflater inflater = (LayoutInflater) mParentFragment.getContext().getSystemService( mParentFragment.getContext().LAYOUT_INFLATER_SERVICE );
//// Inflate and set the layout for the dialog
//// Pass null as the parent view because its going in the dialog
//// layout
//                    builder.setView(inflater.inflate(R.layout.activity_maps, null));
//                    AlertDialog ad = builder.create();
//                    ad.setTitle("Select starting point");
//                    ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            });
//                    ad.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    ad.show();

//                    Intent intent = new Intent(mParentFragment.getContext(),MapsActivity.class);


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
            TableStructure table = new TableStructure(datePickerAlertDialog.getText().toString(), timePickerAlertDialog.getText().toString(), lieu.getText().toString(), description.getText().toString(), nombre.getText().toString());
            String linkId = mRefLink.push().getKey();
            mRefLink.child(linkId).setValue("Table");
            mRefTable.child(linkId).setValue(table);
            Toast.makeText(getContext(), "Table enregistrée", Toast.LENGTH_SHORT).show();
            //finish();
        }

        ((MainActivity)getActivity()).onTableAll();
    }

    public void launchCancel(){((MainActivity)getActivity()).onTableAll();}

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
        ((MainActivity)getActivity()).onTableAll();
        return true;
    }

}
