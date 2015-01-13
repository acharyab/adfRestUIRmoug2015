package com.pinnacol.view;

import com.pinnacol.model.Course;

import java.util.List;

public class SessionData {

    private List<Course> courseList;
    
    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public List<Course> getCourseList() {
        return courseList;
    }    
    
}
