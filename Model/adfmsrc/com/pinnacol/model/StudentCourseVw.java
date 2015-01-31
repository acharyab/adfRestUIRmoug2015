package com.pinnacol.model;

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class StudentCourseVw {
    public StudentCourseVw() {
        super();
    }
    private BigDecimal courseId;
    private Date dob;
    private String gender;
    private BigDecimal gpa;
    private BigDecimal id;
    private String name;
    private String photo;
    private BigDecimal studentId;
    private String year;
    private String courseName;
    private Integer grade;
    private Date completeDate;

    public StudentCourseVw(BigDecimal courseId, Date dob, String gender, BigDecimal gpa, BigDecimal id, String name,
                           String photo, BigDecimal studentId, String year, String courseName, Integer grade, Date completeDate) {
        this.courseId = courseId;
        this.dob = dob;
        this.gender = gender;
        this.gpa = gpa;
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.studentId = studentId;
        this.year = year;
        this.courseName = courseName;
        this.grade = grade;
        this.completeDate = completeDate;
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

    public BigDecimal getGpa() {
        return gpa;
    }

    public void setGpa(BigDecimal gpa) {
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

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

}
