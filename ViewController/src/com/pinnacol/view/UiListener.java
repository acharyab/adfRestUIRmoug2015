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

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;


public class UiListener {
    private RichInputText courseInputText;
    private RichInputText studentId;
    private RichInputText searchMsg;


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
        System.out.println("inside value change");
        
        
        String displayName = (String) courseInputText.getLocalValue();
        System.out.println("display name is: " + displayName);
        
        //ADFContext.getCurrent().getViewScope().put("searchMsg", "Search result for students enrolled in: " + displayName);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(searchMsg);
        
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

    public void getStudentById() {
        
        //resolveExpression("#{bindings.studentsForCourse.collectionModel.makeCurrent}");
        
        DCBindingContainer bindings1 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        // Provide the Iterator Name
        DCIteratorBinding iteratorBinding = (DCIteratorBinding)bindings1.get("studentsForCourseIterator");

        if(iteratorBinding != null){
        Row currentRow = iteratorBinding.getCurrentRow();
        if(currentRow != null){
        //Provide the attribute name
        System.out.println("id is: " + currentRow.getAttribute("studentId"));
        }
       
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("getStudentById");
        System.out.println("query student id is: " +  getStudentId().getLocalValue());//resolveExpression("#{row.studentId}") );
        operationBinding.getParamsMap().put("studentId", getStudentId()); 
        
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            System.out.println("error executing with params");
        } 
    }
    }
    
    
    public static Object resolveExpression(String expression) {
        FacesContext facesContext = FacesContext.getCurrentInstance();  
        //FacesContext facesContext = getFacesContext();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = 
            elFactory.createValueExpression(elContext, expression,
                                            Object.class);
        return valueExp.getValue(elContext);
    }

    public void setStudentId(RichInputText studentId) {
        this.studentId = studentId;
    }

    public RichInputText getStudentId() {
        return studentId;
    }

    public void queryByStudentId(ClientEvent clientEvent) {
        DCBindingContainer bindings1 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        // Provide the Iterator Name
        DCIteratorBinding iteratorBinding = (DCIteratorBinding)bindings1.get("studentsForCourseIterator");

        if(iteratorBinding != null){
        Row currentRow = iteratorBinding.getCurrentRow();
        if(currentRow != null){
        //Provide the attribute name
        System.out.println("id is: " + currentRow.getAttribute("studentId"));
        
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("getStudentById");
    
        operationBinding.getParamsMap().put("studentId", currentRow.getAttribute("studentId")); 
        
        Object result = operationBinding.execute();
        
        if (!operationBinding.getErrors().isEmpty()) {
            System.out.println("error executing with params");
        } 
        }
        }
    }

    public void setSearchMsg(RichInputText searchMsg) {
        this.searchMsg = searchMsg;
    }

    public RichInputText getSearchMsg() {
        return searchMsg;
    }
}
