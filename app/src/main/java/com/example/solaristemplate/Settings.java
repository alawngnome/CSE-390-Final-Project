/**
 * @authors Kevin Chao 112031000 and Samuel Ng 112330868
 * CSE 390 Final Project
 * SolarisTemplate App (Schedule Viewer for Students)
 *
 * Devices : Pixel 2 API 29, OnePlus A6003
 */

package com.example.solaristemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 *
 * I refer to chapters 4-6 of Iversen and Eierman's textbook for setting up this activity.
 *
 * Settings activity for displaying and setting user preferences for course sorting.
 */
public class Settings extends AppCompatActivity {

    /**
     * onCreate method for setting up layout for Settings.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initSettings();
        initSortFieldClick();
        initSortOrderClick();
        initBottomNavigationBar();
    }

    /**
     * Method for initializing settings and accessing SharedPreferences.
     */
    private void initSettings() {
        String sortField = getSharedPreferences("MySchedulePreferences",
                Context.MODE_PRIVATE).getString("sortfield","NAME");
        String sortOrder = getSharedPreferences("MySchedulePreferences",
                Context.MODE_PRIVATE).getString("sortorder","ASC");

        RadioButton rbName = findViewById(R.id.radioBtnName);
        RadioButton rbMajor = findViewById(R.id.radioBtnMajor);
        RadioButton rbCourseNum = findViewById(R.id.radioBtnCourseNum);
        RadioButton rbProfessor = findViewById(R.id.radioBtnProf);
        RadioButton rbTime = findViewById(R.id.radioBtnTime);
        if (sortField.equalsIgnoreCase("NAME")) {
            rbName.setChecked(true);
        }
        else if (sortField.equalsIgnoreCase("MAJOR")) {
            rbMajor.setChecked(true);
        }
        else if (sortField.equalsIgnoreCase("COURSE_NUM")) {
            rbCourseNum.setChecked(true);
        }
        else if (sortField.equalsIgnoreCase("PROFESSOR")) {
            rbProfessor.setChecked(true);
        }
        else {
            rbTime.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.radioBtnAsc);
        RadioButton rbDescending = findViewById(R.id.radioBtnDes);
        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        }
        else {
            rbDescending.setChecked(true);
        }
    }

    /**
     * Method for initializing listeners for the sort field radiogroup.
     */
    private void initSortFieldClick() {
        RadioGroup rgSortField = findViewById(R.id.radioGroupFields);
        rgSortField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbName = findViewById(R.id.radioBtnName);
                RadioButton rbMajor = findViewById(R.id.radioBtnMajor);
                RadioButton rbCourseNum = findViewById(R.id.radioBtnCourseNum);
                RadioButton rbProfessor = findViewById(R.id.radioBtnProf);
                if (rbName.isChecked()) {
                    getSharedPreferences("MySchedulePreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "NAME").apply();
                }
                else if (rbMajor.isChecked()) {
                    getSharedPreferences("MySchedulePreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "MAJOR").apply();
                }
                else if (rbCourseNum.isChecked()) {
                    getSharedPreferences("MySchedulePreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "COURSE_NUM").apply();
                }
                else if (rbProfessor.isChecked()) {
                    getSharedPreferences("MySchedulePreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "PROFESSOR").apply();
                }
                else {
                    getSharedPreferences("MySchedulePreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "TIME").apply();
                }
            }
        });
    }

    /**
     * Method for initializing listeners for the sort order radiogroup.
     */
    private void initSortOrderClick() {
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbAscending = findViewById(R.id.radioBtnAsc);
                if (rbAscending.isChecked()) {
                    getSharedPreferences("MySchedulePreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").apply();
                }
                else {
                    getSharedPreferences("MySchedulePreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").apply();
                }
            }
        });
    }

    /**
     * Method for initializing Bottom Navigation Bar.
     */
    private void initBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set settings tab as selected
        bottomNavigationView.setSelectedItemId(R.id.settings);

        //Set listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });
    }
}