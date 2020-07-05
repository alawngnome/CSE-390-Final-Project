/**
 * @authors Kevin Chao 112031000 and Samuel Ng 112330868
 * CSE 390 Final Project
 * SolarisTemplate App (Schedule Viewer for Students)
 *
 * Devices : Pixel 2 API 29, OnePlus A6003
 */

package com.example.solaristemplate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * I refer to Chapter 5 of Iversen and Eierman's textbook for setting up this database helper.
 *
 * Database helper class for creating a SQLite database
 */
public class ScheduleDBHelper extends SQLiteOpenHelper {

    /**
     * DATABASE_NAME AND DATABASE_VERSION MUST BE PROVIDED FOR SQLITEOPENHELPER'S CONSTRUCTOR
     */
    private static final String DATABASE_NAME = "my_courses.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "course_data";

    /**
     * DATABASE CREATION SQL STATEMENT
     */
    private static final String CREATE_TABLE_ITEM =
            "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT NOT NULL, MAJOR TEXT, "
                    + "COURSE_NUM TEXT, PROFESSOR TEXT, TIME TEXT);";

    /**
     * SCHEDULEDBHELPER CALLS SQLITEOPENHELPER'S CONSTRUCTOR
     * @param context
     */
    public ScheduleDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates a new table
     * @param db SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ITEM);
    }

    /**
     * Upgrades an existing table
     * @param db SQLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // DROPS TABLE
        onCreate(db); // RECREATES TABLE
    }
}
