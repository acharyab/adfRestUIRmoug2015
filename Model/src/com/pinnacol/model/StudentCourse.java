package com.pinnacol.model;

import java.math.BigDecimal;

import java.util.Date;

public class StudentCourse {
    public StudentCourse() {
        super();
    }
    
    private BigDecimal courseId;
    private BigDecimal studentId;


    public StudentCourse(BigDecimal courseId, BigDecimal studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public BigDecimal getCourseId() {
        return courseId;
    }

    public void setCourseId(BigDecimal courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getStudentId() {
        return studentId;
    }

    public void setStudentId(BigDecimal studentId) {
        this.studentId = studentId;
    }


}
