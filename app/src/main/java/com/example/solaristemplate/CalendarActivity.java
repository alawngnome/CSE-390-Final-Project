/**
 * @authors Kevin Chao 112031000 and Samuel Ng 112330868
 * CSE 390 Final Project
 * SolarisTemplate App (Schedule Viewer for Students)
 * Devices : Pixel 2 API 29, OnePlus A6003
 */

package com.example.solaristemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Creates the Calendar Tab, allowing users to tap on specific dates on a calendar,
 * going to a list of classes on a specific day.
 */
public class CalendarActivity extends AppCompatActivity {

    /**
     * The calendar to be displayed.
     */
    CalendarView calendar;


    /**
     * Sets up the calendar, and creates a listener for when a user taps a specific day of the calendar.
     * When this happens, display the list of classes for a specific day.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Initializing CalendarActivity
        calendar = findViewById(R.id.calendar);
        //listener for date selection
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Open list of classes on specific day, through ClassinListActivity
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                String extras = new DateFormatSymbols().getShortWeekdays()[calendar.get(Calendar.DAY_OF_WEEK)];
                Intent intent = new Intent(CalendarActivity.this, ClassInListActivity.class);
                intent.putExtra("dayofweek", extras);
                CalendarActivity.this.startActivity(intent);
            }
        });

        //Initialize and assign variable
        initBottomNavigationBar();
    }

    /**
     * Method for initializing Bottom Navigation Bar.
     */
    private void initBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set dashboard tab as selected
        bottomNavigationView.setSelectedItemId(R.id.calendar);

        //Set listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.calendar:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
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
}