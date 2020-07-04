package com.example.solaristemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //initSettings();
        initBottomNavigationBar();
    }

    private void initSettings() {
        String sortField = getSharedPreferences("MySchedulePreferences",
                Context.MODE_PRIVATE).getString("sortfield","ITEM_NAME");
        String sortOrder = getSharedPreferences("MySchedulePreferences",
                Context.MODE_PRIVATE).getString("sortorder","ASC");
    }

    private void initBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set settings tab as selected
        bottomNavigationView.setSelectedItemId(R.id.settings);

        //Set listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), DashBoard.class));
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