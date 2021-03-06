/**
 * @authors Kevin Chao 112031000 and Samuel Ng 112330868
 * CSE 390 Final Project
 * SolarisTemplate App (Schedule Viewer for Students)
 * Devices : Pixel 2 API 29, OnePlus A6003
 */

package com.example.solaristemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Represents the RecyclerView Activity that appears when clicking on a day in the calendar
 */
public class ClassInListActivity extends AppCompatActivity  implements CourseAdapter.OnNoteListener{
    /**
     * RecyclerView that will appear when clicking on a day in the calendar
     */
    private RecyclerView mRecyclerView;
    /**
     * Course adapter used, same as the MainActivity
     */
    CourseAdapter adapter;
    /**
     * LayoutManager for use alongside RecyclerView
     */
    private RecyclerView.LayoutManager mLayoutManager;
    /**
     * The List holding all the classes for a specific day in the week
     */
    ArrayList<Course> courseList;

    /**
     * On creation, parses through the list of courses in the database, and saves only courses
     * with the same day of week from the date picked on the calendar.
     * Also sets up the RecyclerView, LayoutManager, and Adapter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classes_in_day_list);

        courseList = new ArrayList<>();

        ScheduleDataSource ds = new ScheduleDataSource(this);
        try {
            ds.open();
            String sortField = getSharedPreferences("MySchedulePreferences",
                    Context.MODE_PRIVATE).getString("sortfield","NAME");
            String sortOrder = getSharedPreferences("MySchedulePreferences",
                    Context.MODE_PRIVATE).getString("sortorder","ASC");
            courseList = ds.getCourses(sortField, sortOrder);
            /*Time format will be time + date combined
              Format of time will be MM/DD/YYYY/EEE Hr:Mi*/

            Bundle b = getIntent().getExtras();
            if(b!=null){
                String day = (String)b.get("dayofweek");

                ArrayList<Course> removeList = new ArrayList<>();
                for(Course course: courseList) {
                    String day_fromTime = course.getTime().substring(11,14); //getting EEE from time format
                    if(!day_fromTime.equals(day)) {
                        //courseList.remove(course);
                        removeList.add(course);
                    }
                } //null pointer error because format does not exist yet
                courseList.removeAll(removeList);
            }

            ds.close();
            mRecyclerView = findViewById(R.id.classes_in_day_list);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            //mAdapter = new ClassInListAdapter(courseList);

            adapter = new CourseAdapter(courseList, this, this);

            mRecyclerView.setLayoutManager(mLayoutManager);
            //mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setAdapter(adapter);
        }
        catch(Exception e) {
            Log.d("e.toString","EXCEPTION: " + e.toString());
            Toast.makeText(this, "Error retrieving courses on create", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Creates the delete & edit functionality for classes.
     * @param position of the object in the arraylist to be deleted
     * @param delete determines whether object should be deleted or edited
     */
    @Override
    public void onNoteClick(int position, boolean delete) {
        Course course = courseList.get(position);
        if (delete) {
            ScheduleDataSource ds = new ScheduleDataSource(this);
            try {
                ds.open();
                ds.deleteCourse(course.getCourse_ID());
                ds.close();
                courseList.remove(course);
                adapter.notifyItemRemoved(position);
            }
            catch (Exception e) {
                Toast.makeText(this, "Error deleting course", Toast.LENGTH_LONG).show();
            }
        }
        else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            AddCourseDialog newFragment = new AddCourseDialog(course, courseList, adapter);
            newFragment.show(fragmentManager, "Add Course Dialog");
        }
    }
}
