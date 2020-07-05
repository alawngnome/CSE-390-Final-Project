/**
 * @authors Kevin Chao 112031000 and Samuel Ng 112330868
 * CSE 390 Final Project
 * SolarisTemplate App (Schedule Viewer for Students)
 *
 * Devices : Pixel 2 API 29, OnePlus A6003
 */

package com.example.solaristemplate;

public class Course {
    private int course_ID; // Unique course ID
    private String name; // Name of course
    private String major; // Course major
    private String course_num; // Course number
    private String professor; // Name of professor
    private String time; // Course time

    /**
     * Constructor initializes all new courses' IDs to -1
     */
    public Course() {
        course_ID = -1;
    }

    /**
     * Getters and setters for all fields
     */
    public int getCourse_ID() {
        return course_ID;
    }

    public void setCourse_ID(int course_ID) {
        this.course_ID = course_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCourse_num() {
        return course_num;
    }

    public void setCourse_num(String course_num) {
        this.course_num = course_num;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
