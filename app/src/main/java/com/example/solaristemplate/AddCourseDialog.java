/**
 * @authors Kevin Chao 112031000 and Samuel Ng 112330868
 * CSE 390 Final Project
 * SolarisTemplate App (Schedule Viewer for Students)
 *
 * Devices : Pixel 2 API 29, OnePlus A6003
 */

package com.example.solaristemplate;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * I refer to Chapters 4-6 of Iversen and Eierman's textbook for setting up this class.
 *
 * DialogFragment for adding courses into the database.
 */
public class AddCourseDialog extends DialogFragment implements DatePickerDialog.SaveDateListener, TimePickerDialog.SaveTimeListener {
    /**
     * Course received from MainActivity or newly created course.
     */
    private Course course;
    /**
     * List of courses received from MainActivity
     */
    private ArrayList<Course> courses;
    /**
     * Adapter received from MainActivity
     */
    private CourseAdapter adapter;

    /**
     * Constructor for AddCourseDialog
     * @param course course for editing or null
     * @param courses list of courses
     * @param adapter adapter for RecyclerView
     */
    public AddCourseDialog(Course course, ArrayList<Course> courses, CourseAdapter adapter) {
        this.course = course;
        this.courses = courses;
        this.adapter = adapter;
    }

    /**
     * Initializes and returns inflated view of the AddCourseDialog
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_course_dialog, container);

        final EditText nameET = view.findViewById(R.id.nameET);
        final EditText majorET = view.findViewById(R.id.majorET);
        final EditText courseNumET = view.findViewById(R.id.courseNumET);
        final EditText professorET = view.findViewById(R.id.professorET);
        final TextView dateTV = view.findViewById(R.id.dateTV);
        final TextView timeTV = view.findViewById(R.id.timeTV);

        Button change_button_date = view.findViewById(R.id.dateChangeBtn);
        change_button_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getChildFragmentManager();
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.show(fm, "Date Picker Dialog");
            }
        });
        Button change_button_time = view.findViewById(R.id.timeChangeBtn);
        change_button_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getChildFragmentManager();
                TimePickerDialog timePickerDialog = new TimePickerDialog();
                timePickerDialog.show(fm, "Time Picker Dialog");
            }
        });

        Button save_button = view.findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                String major = majorET.getText().toString();
                String courseNum = courseNumET.getText().toString();
                String professor = professorET.getText().toString();
                String date = dateTV.getText().toString();
                String time = timeTV.getText().toString();
                String date_time = date + " " + time;
                ScheduleDataSource ds = new ScheduleDataSource(view.getContext());
                if (name.equals("") || major.equals("") || courseNum.equals("") || professor.equals("") || date.equals("") || time.equals("")) {
                    getDialog().dismiss();
                    return;
                }
                if (course == null) {
                    course = new Course();
                }
                course.setName(name);
                course.setMajor(major);
                course.setCourse_num(courseNum);
                course.setProfessor(professor);
                course.setTime(date_time);

                try {
                    ds.open();
                    if (course.getCourse_ID() == -1) {
                        ds.insertCourse(course);
                        course.setCourse_ID(ds.getLastCourseID());
                        courses.add(course);
                        Log.d("COURSE ADDED", "COURSE ADDED");
                    }
                    else {
                        ds.updateCourse(course);
                        Log.d("COURSE UPDATED", "COURSE UPDATED");
                    }
                    adapter.notifyDataSetChanged();
                    ds.close();
                }
                catch (Exception e) {
                    Toast.makeText(view.getContext(), "Error inserting item", Toast.LENGTH_LONG).show();
                }
                getDialog().dismiss();
            }
        });
        Button cancel_button = view.findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    /**
     * Receives date from DatePickerDialog
     * @param date
     */
    @Override
    public void didFinishDatePickerDialog(Calendar date) {
        TextView dateTV = getView().findViewById(R.id.dateTV);
        dateTV.setText(DateFormat.format("MM/dd/yyyy/EEE", date));
    }

    /**
     * Receives time from TimePickerDialog
     * @param time
     */
    @Override
    public void didFinishTimePickerDialog(String time) {
        TextView timeTV = getView().findViewById(R.id.timeTV);
        timeTV.setText(time);
    }
}
