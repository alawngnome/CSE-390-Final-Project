package com.example.solaristemplate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerDialog extends DialogFragment {
    String time;

    public interface SaveTimeListener {
        void didFinishTimePickerDialog(String time);
    }

    public TimePickerDialog() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.time_picker_dialog, container);

        getDialog().setTitle("Select Time");
        time = "";

        final TimePicker timePicker = view.findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay + ":" + minute;
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
