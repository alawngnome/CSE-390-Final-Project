/**
 * @authors Kevin Chao 112031000 and Samuel Ng 112330868
 * CSE 390 Final Project
 * SolarisTemplate App (Schedule Viewer for Students)
 */

package com.example.solaristemplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * I refer to Chapter 5 of Iversen and Eierman's textbook for setting up this datasource class.
 *
 * Datasource class for connecting RecyclerView to the database.
 */
public class ScheduleDataSource {
    /**
     * INSTANCES OF SQLITEDATABASE AND SCHEDULEDBHELPER CLASSES
     */
    private SQLiteDatabase db;
    private ScheduleDBHelper dbHelper;

    public ScheduleDataSource(Context context) {
        dbHelper = new ScheduleDBHelper(context);
    }

    /**
     * METHOD FOR ACCESSING DATABASE
     * @throws SQLException
     */
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    /**
     * METHOD FOR CLOSING ACCESS TO DATABASE
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Method for inserting course into the database.
     * @param course
     * @return true on success, false otherwise
     */
    public boolean insertCourse(Course course) {
        boolean success_flag = false;
        try  {
            /* The following key/value pairs are used to insert items into the database
                column names are the keys
             */
            ContentValues initialValues = new ContentValues();
            initialValues.put("NAME", course.getName());
            initialValues.put("MAJOR", course.getMajor());
            initialValues.put("COURSE_NUM", course.getCourse_num());
            initialValues.put("PROFESSOR", course.getProfessor());
            initialValues.put("TIME", course.getTime());

            // ID of the newly created row is returned by db.insert()
            success_flag = db.insert(ScheduleDBHelper.TABLE_NAME, null, initialValues) > 0;
        }
        catch (Exception e) {
            // success_flag will return false upon exception
        }
        return success_flag;
    }

    /**
     * Method for deleting course from the database.
     * @param courseID unique ID of the course
     * @return true on success, false otherwise
     */
    public boolean deleteCourse(int courseID) {
        boolean success_flag = false;
        try {
            success_flag = db.delete(ScheduleDBHelper.TABLE_NAME, "_id=" + courseID, null) > 0;
        }
        catch (Exception e) {
            // success_flag will return false upon exception
        }
        return success_flag;
    }

    /**
     * Method for updating course in the database.
     * @param course
     * @return true on success, false otherwise
     */
    public boolean updateCourse(Course course) {
        boolean success_flag = false;
        try  {
            Long rowID = (long) course.getCourse_ID();

            ContentValues updateValues = new ContentValues();
            updateValues.put("NAME", course.getName());
            updateValues.put("MAJOR", course.getMajor());
            updateValues.put("COURSE_NUM", course.getCourse_num());
            updateValues.put("PROFESSOR", course.getProfessor());
            updateValues.put("TIME", course.getTime());

            // # of updated rows is returned by db.update()
            success_flag = db.update(ScheduleDBHelper.TABLE_NAME, updateValues, "_id=" + rowID, null) > 0;
        }
        catch (Exception e) {
            // success_flag will return false upon exception
        }
        return success_flag;
    }

    /**
     * Method for accessing the list of courses in the database.
     * @param sort_field String that determines which field the list is sorted by
     * @param sort_order String that determines which order to sort the list
     * @return ArrayList of courses
     */
    public ArrayList<Course> getCourses(String sort_field, String sort_order) {
        ArrayList<Course> courses = new ArrayList<Course>();
        try {
            String query = "SELECT  * FROM " + ScheduleDBHelper.TABLE_NAME + " ORDER BY " + sort_field + " " + sort_order;
            Cursor cursor = db.rawQuery(query, null);

            Course newCourse;
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                newCourse = new Course();
                newCourse.setCourse_ID(cursor.getInt(0));
                newCourse.setName(cursor.getString(1));
                newCourse.setMajor(cursor.getString(2));
                newCourse.setCourse_num(cursor.getString(3));
                newCourse.setProfessor(cursor.getString(4));
                newCourse.setTime(cursor.getString(5));
                courses.add(newCourse);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            courses = new ArrayList<Course>();
        }
        return courses;
    }
}
