package com.example.uqac.unilink;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Folgar on 02/12/2017.
 */

public class CreateSortieActivity extends AppCompatActivity  {

    private static final String DIALOG_DATE = "sortie.DateDialog";
    private EditText datePickerAlertDialog;
    private EditText timePickerAlertDialog;
    private EditText nombre;
    private EditText lieu;
    private EditText description;
    private Button ok_button;
    private Button cancel_button;
    private DatabaseReference mRefTable;
    private DatabaseReference mRefLink;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards_sorties);
        datePickerAlertDialog = (EditText) findViewById(R.id.alert_dialog_date_picker);
        timePickerAlertDialog = (EditText) findViewById(R.id.alert_dialog_time_picker);
        nombre = (EditText) findViewById(R.id.nombre_participants);
        lieu = (EditText) findViewById(R.id.lieu);
        description = (EditText) findViewById(R.id.description);
        ok_button = (Button) findViewById(R.id.ok_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        mRefTable = FirebaseDatabase.getInstance().getReference("sortie");
        mRefLink = FirebaseDatabase.getInstance().getReference("link");

        datePickerAlertDialog.setText("okaaaaaaaaay");

        datePickerAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("plop", "il passe dedans");
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(getSupportFragmentManager(), DIALOG_DATE);
            }
        });


        timePickerAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerFragment dialog = new TimePickerFragment();
                dialog.show(getSupportFragmentManager(), "TimePickerFragment");
            }
        });
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datePickerAlertDialog.getText().toString().matches(""))
                    Toast.makeText(getApplicationContext(), "Date manquante", Toast.LENGTH_SHORT).show();
                else {
                    if (timePickerAlertDialog.getText().toString().matches(""))
                        Toast.makeText(getApplicationContext(), "Heure manquante", Toast.LENGTH_SHORT).show();
                    else {
                        if (lieu.getText().toString().matches(""))
                            Toast.makeText(getApplicationContext(), "Lieu manquant", Toast.LENGTH_SHORT).show();
                        else if (nombre.getText().toString().matches(""))
                            Toast.makeText(getApplicationContext(), "Nombre de participants maximum manquant", Toast.LENGTH_SHORT).show();
                        else {
                            SortieStructure sortie = new SortieStructure(datePickerAlertDialog.getText().toString(), timePickerAlertDialog.getText().toString(), lieu.getText().toString(), description.getText().toString(), nombre.getText().toString());
                            String linkId = mRefLink.push().getKey();
                            mRefLink.child(linkId).setValue("Sortie");
                            mRefTable.child(linkId).setValue(sortie);
                            Toast.makeText(getApplicationContext(), "Sortie enregistrée", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }

        });

        cancel_button.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void onFinishDialog(Date date) {
        datePickerAlertDialog.setText(formatDate(date));
    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hireDate = sdf.format(date);
        return hireDate;
    }


    public void onFinishDialog(String time) {
        timePickerAlertDialog.setText(time);
    }
}

