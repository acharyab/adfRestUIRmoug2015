package com.pinnacol.view;

import com.pinnacol.client.CourseClient;
import com.pinnacol.model.Course;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.model.AutoSuggestUIHints;

public class AutoSuggest {
    public AutoSuggest() {
    }

    public List SuggestCourses(FacesContext facesContext, AutoSuggestUIHints autoSuggestUIHints) {
        
        List<Course> courseList = null;        

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        SessionData sessionData = (SessionData) ec.getSessionMap().get("sessionDataBean"); 
       
        if (sessionData == null){
            sessionData = new SessionData();
          try {
              courseList = CourseClient.getCourses();
              sessionData.setCourseList(courseList);
              ec.getSessionMap().put("sessionDataBean", sessionData);
          } catch (Exception e) {
              System.out.println("Error retrieving courses");
          }
        } else {
          courseList = sessionData.getCourseList();  //new ArrayList<Course>();
        }
        
        List<SelectItem> inList = new ArrayList<SelectItem>();
        
        for (Course c : courseList){
            String displayName = c.getCourseNumber() + " - " + c.getName() + " (" + c.getInstructor() + ")";
            inList.add( new SelectItem((displayName)));
        }
      
        List<SelectItem> outList = new ArrayList<SelectItem>();
        
        for (SelectItem item : inList) {
            if (item.getLabel().contains(autoSuggestUIHints.getSubmittedValue())) {
                outList.add(item);
            }
        }
        
        return outList;
    }
}
