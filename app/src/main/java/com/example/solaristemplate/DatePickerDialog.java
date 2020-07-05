package com.example.solaristemplate;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerDialog extends DialogFragment {
    Calendar selectedDate;

    public interface SaveDateListener() {
        void didFinishDatePickerDialog(Calendar date);
    }
}
