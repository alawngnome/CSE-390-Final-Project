/**
 * @authors Kevin Chao 112031000 and Samuel Ng 112330868
 * CSE 390 Final Project
 * SolarisTemplate App (Schedule Viewer for Students)
 */

package com.example.solaristemplate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * I refer to Chapter 4 of Iversen and Eierman's textbook for setting up this DatePickerDialog.
 *
 * Child fragment for the DatePickerDialog.
 */
public class DatePickerDialog extends DialogFragment {
    Calendar selectedDate;

    /**
     * Interface for passing selected date to the parent fragment.
     */
    public interface SaveDateListener {
        void didFinishDatePickerDialog(Calendar date);
    }

    /**
     * Empty constructor required for DialogFragment
     */
    public DatePickerDialog() {
    }

    /**
     * Initializes the DatePickerDialog view and returns the inflated view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return inflated view of the DatePickerDialog
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.date_picker_dialog, container);

        getDialog().setTitle("Select Date");
        selectedDate = Calendar.getInstance();

        final CalendarView cv = view.findViewById(R.id.calendar_dialog);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                selectedDate.set(year, month, day);
            }
        });

        Button saveButton = view.findViewById(R.id.saveBtn_date);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveDateListener fragment = (SaveDateListener) getParentFragment();
                fragment.didFinishDatePickerDialog(selectedDate);
                getDialog().dismiss();
            }
        });
        Button cancelButton = view.findViewById(R.id.cancelBtn_date);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }
}
