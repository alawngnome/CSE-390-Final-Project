package com.example.solaristemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashBoard extends AppCompatActivity {

    CalendarView calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        //Initializing Calendar
        calendar = findViewById(R.id.calendar);
        //listener for date selection
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //create new database object/event here
            }
        });

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
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