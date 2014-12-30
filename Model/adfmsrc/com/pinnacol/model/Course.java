package com.pinnacol.model;

import java.math.BigDecimal;


public class Course {
    
    private String courseNumber;
    private Integer credits;
    private BigDecimal id;
    private String instructor;
    private String name;
    private String schedule;
    
    public Course() {
        super();
    }
    
    public Course(String courseNumber, Integer credits, BigDecimal id, String instructor, String name,
                  String schedule) {
        this.courseNumber = courseNumber;
        this.credits = credits;
        this.id = id;
        this.instructor = instructor;
        this.name = name;
        this.schedule = schedule;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
