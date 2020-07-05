package com.example.solaristemplate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class AddCourseDialog extends DialogFragment {
    private Course course; // course received from MainActivity or newly created course
    private ArrayList<Course> courses; // list of courses received from MainActivity
    private CourseAdapter adapter; // adapter received from MainActivity

    public AddCourseDialog(Course course, ArrayList<Course> courses, CourseAdapter adapter) {
        this.course = course;
        this.courses = courses;
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_course_dialog, container);

        final EditText nameET = view.findViewById(R.id.nameET);
        final EditText majorET = view.findViewById(R.id.majorET);
        final EditText courseNumET = view.findViewById(R.id.courseNumET);
        final EditText professorET = view.findViewById(R.id.professorET);
        final EditText timeET = view.findViewById(R.id.timeET);

        Button save_button = view.findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (course == null) {
                    course = new Course();
                }
                String name = nameET.getText().toString();
                String major = majorET.getText().toString();
                String courseNum = courseNumET.getText().toString();
                String professor = professorET.getText().toString();
                String time = timeET.getText().toString();
                if (name.equals("") || major.equals("") || courseNum.equals("") || professor.equals("") || time.equals("")) {
                    getDialog().dismiss();
                    return;
                }
                course.setName(name);
                course.setMajor(major);
                course.setCourse_num(courseNum);
                course.setProfessor(professor);
                course.setTime(time);

                ScheduleDataSource ds = new ScheduleDataSource(view.getContext());
                try {
                    ds.open();
                    if (course.getCourse_ID() == -1) {
                        ds.insertCourse(course);
                        courses.add(course);
                    }
                    else {
                        ds.updateCourse(course);
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
}
