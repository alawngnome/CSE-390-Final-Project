package com.example.solaristemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassInListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classes_in_day_list);

        ArrayList<Course> courseList = new ArrayList<>();

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
                for(Course course: courseList) {
                    String day_fromTime = course.getTime().substring(11,14); //geting EEE from time format
                    if(!day_fromTime.equals(day)) {
                        courseList.remove(course);
                    }
                } //null pointer error because format does not exist yet
            }

            ds.close();
            mRecyclerView = findViewById(R.id.classes_in_day_list);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new ClassInListAdapter(courseList);

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
        catch(Exception e) {
            Toast.makeText(this, "Error retrieving courses on create", Toast.LENGTH_LONG).show();
        }
    }
}
