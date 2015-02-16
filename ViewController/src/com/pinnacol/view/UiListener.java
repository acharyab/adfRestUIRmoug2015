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
import oracle.adf.view.rich.component.rich.data.RichListView;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;


import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;


import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.faces.application.FacesMessage;

import oracle.jbo.ViewObject;


public class UiListener {
    private RichInputText courseInputText;
    private RichInputText searchMsg;
    private RichPanelFormLayout studentDetailForm;
    private RichListView studentList;
    private RichPanelBox studentListPBox;
    private RichInputDate detailDOB;
    private RichInputText testName;


    public UiListener() {
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    private void queryStudents(String id) {
        BigDecimal courseId = new BigDecimal(id);

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        operationBinding.getParamsMap().put("courseId", courseId);

        Object result = operationBinding.execute();
        ADFContext.getCurrent().getViewScope().put("queriedCourseId", courseId);
        if (!operationBinding.getErrors().isEmpty()) {
            System.out.println("error executing with params");
        }
    }
   
    private void queryCoursesByStudent(String stId){
         BigDecimal studentId = new BigDecimal (stId);
         
         BindingContainer bindings = getBindings();
         OperationBinding operationBinding = bindings.getOperationBinding("coursesByStudent");
         operationBinding.getParamsMap().put("studentId", studentId); 
         
         Object result = operationBinding.execute();
       
         if (!operationBinding.getErrors().isEmpty()) {
             System.out.println("error executing with params");
         }         
        
     }
    

    public void CourseChangeListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("inside value change");
               
        String displayName = (String) courseInputText.getLocalValue();
        System.out.println("display name is: " + displayName);
        
        ADFContext.getCurrent().getViewScope().put("searchMsg", "Students enrolled in: " + displayName);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchMsg);
        
        String id = null;
        String stId = null;
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
        
        
        DCBindingContainer bindings1 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        // Provide the Iterator Name
        DCIteratorBinding iteratorBinding = (DCIteratorBinding)bindings1.get("studentsForCourseIterator");

        if(iteratorBinding != null){
        Row currentRow = iteratorBinding.getCurrentRow();
        if(currentRow != null){
        //Provide the attribute name
        System.out.println("id is: " + currentRow.getAttribute("studentId"));
        }
            queryCoursesByStudent(currentRow.getAttribute("studentId").toString());
        }
        
        
    }

    public void setCourseInputText(RichInputText courseInputText) {
        this.courseInputText = courseInputText;
    }

    public RichInputText getCourseInputText() {
        return courseInputText;
    }



    public void setSearchMsg(RichInputText searchMsg) {
        this.searchMsg = searchMsg;
    }

    public RichInputText getSearchMsg() {
        return searchMsg;
    }

 /*   public void setStudntId(RichOutputFormatted studntId) {
        this.studntId = studntId;
    }

    public RichOutputFormatted getStudntId() {
        return studntId;
    } */

    public void setStudentScoreGraph(SelectionEvent selectionEvent) {
        invokeEL("#{bindings.studentsForCourse.collectionModel.makeCurrent}", new Class[] {SelectionEvent.class}, new Object[] { selectionEvent }); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(studentListPBox); 
    
        DCBindingContainer bindings1 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iteratorBinding = (DCIteratorBinding)bindings1.get("studentsForCourseIterator");

        if(iteratorBinding != null){
        Row currentRow = iteratorBinding.getCurrentRow();
        if(currentRow != null){
        System.out.println("id is: " + currentRow.getAttribute("studentId"));
        }
            queryCoursesByStudent(currentRow.getAttribute("studentId").toString());
            getStudentDetails(currentRow.getAttribute("studentId").toString());           
        }      
    }

    public static Object invokeEL(String el, Class[] paramTypes, Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);

        return exp.invoke(elContext, params);
    }

    public void setStudentDetailForm(RichPanelFormLayout studentDetailForm) {
        this.studentDetailForm = studentDetailForm;
    }

    public RichPanelFormLayout getStudentDetailForm() {
        return studentDetailForm;
    }

    public void getStudentsNotEnrolled() {        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("studentsNotEnrolled");
        operationBinding.getParamsMap().put("courseId", getCourseId()); 
        
        System.out.println("before execute: ");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            System.out.println("error executing with params");
        } 
    }
    
    private BigDecimal getCourseId(){
        
        BigDecimal courseId = null;
        
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();              
        DCIteratorBinding iteratorBinding = (DCIteratorBinding) bindings1.get("studentsForCourseIterator");

        if (iteratorBinding != null) {
            Row currentRow = iteratorBinding.getCurrentRow();
            if (currentRow != null) {
                courseId = new BigDecimal(currentRow.getAttribute("courseId").toString());
                System.out.println("Course id is: " + currentRow.getAttribute("courseId"));
            }
        }
        
        return courseId;
    }

    public void deleteEnrolledStudent(ActionEvent actionEvent) {
                
                DCBindingContainer bc = (DCBindingContainer) getBindings();
                DCIteratorBinding rowIter1 = bc.findIteratorBinding("studentsForCourseIterator");
                
               
                Row r = rowIter1.getCurrentRow();
                String studentNameR = (String) r.getAttribute("name");
                System.out.println("row name " + studentNameR);

                RowSetIterator rowSIter1 = rowIter1.getRowSetIterator();
                Row r1 = rowSIter1.getCurrentRow();
                String studentName = (String) r1.getAttribute("name");
                String studentCourseId = ((BigDecimal) r1.getAttribute("id")).toString();
                int rowId = studentList.getRowIndex();
                System.out.println("delete name " + studentName + ", studentCourseId " + studentCourseId + " current row key is: " + rowId);
               
                String deleteUrl = "http://127.0.0.1:7101/adfRestServiceRmoug2015-service-context-root/resources/service/studentCourse/" + studentCourseId;
                Client client = Client.create();
                WebResource webResource = client.resource(deleteUrl);
             
                ClientResponse response = webResource.accept("application/json").delete(ClientResponse.class);

               if (response.getStatus() != 200) {
                   FacesMessage msg = new FacesMessage("Delete Status", "Delete Failed : HTTP error code : " + response.getStatus());
                   FacesContext.getCurrentInstance().addMessage("Delete status ", msg);
               }
                
                if (response.getStatus() == 200) {
                   
                   ViewObject vo = rowIter1.getViewObject();
                   vo.removeCurrentRow();
                   AdfFacesContext.getCurrentInstance().addPartialTarget(studentListPBox);

                   FacesMessage msg = new FacesMessage("Delete Status", studentName + " successfully un-enrolled");
                   //FacesContext.getCurrentInstance().addMessage("Delete status ", msg);

                }
    }
    
    private void getStudentDetails(String studentId){
         
         BindingContainer bindings = getBindings();
         OperationBinding operationBinding = bindings.getOperationBinding("getStudentDetails");
         operationBinding.getParamsMap().put("studentId", studentId); 
         
         Object result = operationBinding.execute();
         if (!operationBinding.getErrors().isEmpty()) {
             System.out.println("error retrieving student details");
         }         
        
     }

    public void setStudentList(RichListView studentList) {
        this.studentList = studentList;
    }

    public RichListView getStudentList() {
        return studentList;
    }

    public void setStudentListPBox(RichPanelBox studentListPBox) {
        this.studentListPBox = studentListPBox;
    }

    public RichPanelBox getStudentListPBox() {
        return studentListPBox;
    }

    public void setDetailDOB(RichInputDate detailDOB) {
        this.detailDOB = detailDOB;
    }

    public RichInputDate getDetailDOB() {
        return detailDOB;
    }

    public void setTestName(RichInputText testName) {
        this.testName = testName;
    }

    public RichInputText getTestName() {
        return testName;
    }
}
