package com.example.solaristemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CourseAdapter.OnNoteListener {
    CourseAdapter adapter; // Instance of CourseAdapter used for updating recyclerView
    ArrayList<Course> courses; // ArrayList of courses used for populating the recyclerView with courses

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAddButton();
        initBottomNavigationBar();

        ScheduleDataSource ds = new ScheduleDataSource(this);
        try {
            ds.open();
            String sortField = getSharedPreferences("MySchedulePreferences",
                    Context.MODE_PRIVATE).getString("sortfield","NAME");
            String sortOrder = getSharedPreferences("MySchedulePreferences",
                    Context.MODE_PRIVATE).getString("sortorder","ASC");
            courses = ds.getCourses(sortField, sortOrder);
            ds.close();
            RecyclerView course_recycler = findViewById(R.id.homeList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            course_recycler.setLayoutManager(layoutManager);
            adapter = new CourseAdapter(courses, this, this);
            course_recycler.setAdapter(adapter);
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving courses on create", Toast.LENGTH_LONG).show();
        }
    }

    private void initAddButton() {
        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                AddCourseDialog newFragment = new AddCourseDialog(null, courses, adapter);
                newFragment.show(fragmentManager, "Add Course Dialog");
            }
        });
    }

    private void initBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set home tab as selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Set listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(), Calendar.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onNoteClick(int position, boolean delete) {
        Course course = courses.get(position);
        if (delete) {
            ScheduleDataSource ds = new ScheduleDataSource(this);
            try {
                ds.open();
                ds.deleteCourse(course.getCourse_ID());
                ds.close();
                courses.remove(course);
                adapter.notifyItemRemoved(position);
            }
            catch (Exception e) {
                Toast.makeText(this, "Error deleting course", Toast.LENGTH_LONG).show();
            }
        }
        else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            AddCourseDialog newFragment = new AddCourseDialog(course, courses, adapter);
            newFragment.show(fragmentManager, "Add Course Dialog");
        }
    }
}