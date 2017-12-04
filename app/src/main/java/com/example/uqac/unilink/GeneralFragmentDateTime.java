package com.example.uqac.unilink;

import android.support.v4.app.Fragment;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lorane on 03/12/2017.
 */

public abstract class GeneralFragmentDateTime extends GeneralFragment {

    public void onFinishDialog(Date date) {}

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hireDate = sdf.format(date);
        return hireDate;
    }

    public abstract void onFinishDialog(String time, EditText timePickerAlertDialog);
    public void onTimePicker(EditText timePickerAlertDialog){}
}
