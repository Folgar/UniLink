package com.example.uqac.unilink;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Date;


public class table_ouverte extends AppCompatActivity implements DatePickerFragment.DateDialogListener,TimePickerFragment.TimeDialogListener{

    private static final String DIALOG_DATE = "table_ouverte.DateDialog";
    private EditText datePickerAlertDialog;
    private EditText timePickerAlertDialog;
    private EditText nombre;
    private EditText lieu;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_ouverte);
        datePickerAlertDialog = (EditText)findViewById(R.id.alert_dialog_date_picker);
        timePickerAlertDialog = (EditText)findViewById(R.id.alert_dialog_time_picker);
        nombre = (EditText)findViewById(R.id.nombre_participants);
        lieu = (EditText)findViewById(R.id.lieu);

        button = (Button)findViewById(R.id.validate);
        datePickerAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(getSupportFragmentManager(), DIALOG_DATE);
            }
        });

        timePickerAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerFragment dialog = new TimePickerFragment();
                dialog.show(getSupportFragmentManager(),"TimePickerFragment");
            }
        });
        timePickerAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerFragment dialog = new TimePickerFragment();
                dialog.show(getSupportFragmentManager(),"TimePickerFragment");
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
