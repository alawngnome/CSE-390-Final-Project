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

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendar;


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