package com.pinnacol.view;

import com.pinnacol.client.CourseClient;
import com.pinnacol.model.Course;

import java.math.BigDecimal;

import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class UiListener {
    private RichInputText courseInputText;

    public UiListener() {
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
   private void queryStudents(String id){
        BigDecimal courseId = new BigDecimal (id);
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        operationBinding.getParamsMap().put("courseId", courseId); 
        
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            System.out.println("error executing with params");
        }         
       
    }
    

    public void CourseChangeListener(ValueChangeEvent valueChangeEvent) {
        
        String displayName = (String) courseInputText.getLocalValue();
        String id = null;
        List<Course> courseList = null; 
        
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        SessionData sessionData = (SessionData) ec.getSessionMap().get("sessionDataBean"); 
        
        if (sessionData == null){   //just in case, should not be null
            sessionData = new SessionData();
        try {
            courseList = CourseClient.getCourses();
            sessionData.setCourseList(courseList);
            ec.getSessionMap().put("sessionDataBean", sessionData);
        } catch (Exception e) {
            System.out.println("Error retrieving courses");
        }
        } else {  //should always get here, as sessionData populated in AutoSuggest
            courseList = sessionData.getCourseList(); 
        }
        
        for (Course course : courseList){
        if (displayName.contains(course.getCourseNumber())){
          id = course.getId().toString();  
          break;
        }
        }
        queryStudents(id);
    }

    public void setCourseInputText(RichInputText courseInputText) {
        this.courseInputText = courseInputText;
    }

    public RichInputText getCourseInputText() {
        return courseInputText;
    }
}
