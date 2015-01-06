package com.pinnacol.model;

import java.math.BigDecimal;

import java.util.Date;

public class StudentCourseVw {
    public StudentCourseVw() {
        super();
    }
    private BigDecimal courseId;
    private Date dob;
    private String gender;
    private Integer gpa;
    private BigDecimal id;
    private String name;
    private String photo;
    private BigDecimal studentId;
    private String year;

    public StudentCourseVw(BigDecimal courseId, Date dob, String gender, Integer gpa, BigDecimal id, String name,
                           String photo, BigDecimal studentId, String year) {
        this.courseId = courseId;
        this.dob = dob;
        this.gender = gender;
        this.gpa = gpa;
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.studentId = studentId;
        this.year = year;
    }

    public BigDecimal getCourseId() {
        return courseId;
    }

    public void setCourseId(BigDecimal courseId) {
        this.courseId = courseId;
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

    public BigDecimal getStudentId() {
        return studentId;
    }

    public void setStudentId(BigDecimal studentId) {
        this.studentId = studentId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
