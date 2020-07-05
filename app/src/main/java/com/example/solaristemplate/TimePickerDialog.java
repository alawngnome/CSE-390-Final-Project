/**
 * @authors Kevin Chao 112031000 and Samuel Ng 112330868
 * CSE 390 Final Project
 * SolarisTemplate App (Schedule Viewer for Students)
 *
 * Devices : Pixel 2 API 29, OnePlus A6003
 *
 * TimePicker methods requires API 23 and higher!!!
 */

package com.example.solaristemplate;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

/**
 * Child fragment for TimePickerDialog
 */
public class TimePickerDialog extends DialogFragment {
    /**
     * String to hold the time in HH:MI format
     */
    String time;

    /**
     * Interface for passing time to parent fragment
     */
    public interface SaveTimeListener {
        void didFinishTimePickerDialog(String time);
    }

    /**
     * Empty constructor for TimePickerDialog
     */
    public TimePickerDialog() {}

    /**
     * Initializes the TimePickerDialog view and returns the inflated view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return TimePickerDialog view
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.time_picker_dialog, container);

        getDialog().setTitle("Select Time");

        final TimePicker timePicker = view.findViewById(R.id.timePicker);
        timePicker.setHour(0);
        timePicker.setMinute(0);
        int defaultHour = timePicker.getHour();
        int defaultMin = timePicker.getMinute();
        time = String.format("%02d:%02d", defaultHour, defaultMin);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time = String.format("%02d:%02d", hourOfDay, minute);
            }
        });

        Button saveButton = view.findViewById(R.id.saveBtn_time);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.SaveTimeListener fragment = (TimePickerDialog.SaveTimeListener) getParentFragment();
                fragment.didFinishTimePickerDialog(time);
                getDialog().dismiss();
            }
        });
        Button cancelButton = view.findViewById(R.id.cancelBtn_time);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }
}
