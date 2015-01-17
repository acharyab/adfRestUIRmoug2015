package com.pinnacol.model;

import java.math.BigDecimal;

import java.util.Date;

public class Student {
    private Date dob;
    private String gender;
    private Integer gpa;
    private BigDecimal id;
    private String name;
    private String photo;
    private String year;

    public Student() {
    }

    public Student(Date dob, String gender, Integer gpa, BigDecimal id, String name, String photo, String year) {
        this.dob = dob;
        this.gender = gender;
        this.gpa = gpa;
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.year = year;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getGpa() {
        return gpa;
    }

    public void setGpa(Integer gpa) {
        this.gpa = gpa;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    
    
    
}
