package com.pinnacol.view;

import com.pinnacol.model.Student;

import com.pinnacol.model.StudentCourse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import java.math.BigDecimal;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class EnrollStudent {

    private RichPanelGroupLayout studentListPGL;
    private RichPanelBox studentLIstPB;

    public EnrollStudent() {
    }

    public String enrollStudent() {
        
        StudentCourse enrollStudent = new StudentCourse();

        enrollStudent.setCourseId(getCourseId());
        enrollStudent.setStudentId(getStudentId());

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client client = Client.create(clientConfig);

        WebResource webResource =
            client.resource("http://127.0.0.1:7101/adfRestServiceRmoug2015-service-context-root/resources/service/studentCourse");
        ClientResponse response =
            webResource.accept("application/json").type("application/json").post(ClientResponse.class, enrollStudent);

        if (response.getStatus() != 200) {
            FacesMessage msg =
                new FacesMessage("Transaction Status",
                                 "Transaction Failed : HTTP error code : " + response.getStatus());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("Student status ", msg);
        }

        if (response.getStatus() == 200) {
            queryStudents(getCourseId().toString());
            AdfFacesContext.getCurrentInstance().addPartialTarget(studentListPGL);
            getStudentsNotEnrolled(); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(studentLIstPB);
        }


        return null;
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
    
    private BigDecimal getStudentId(){
        
        BigDecimal studentId = null;
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        
        
        DCIteratorBinding iteratorBinding = (DCIteratorBinding) bindings1.get("studentNotEnrolledIterator");

        if (iteratorBinding != null) {
            Row currentRow = iteratorBinding.getCurrentRow();
            if (currentRow != null) {
                studentId = new BigDecimal(currentRow.getAttribute("id").toString());
                System.out.println("student id is: " + currentRow.getAttribute("id"));
            }
        }
        
        return studentId;
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

    private String getStudentName(){
        
        String studentName = null;
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        
        
        DCIteratorBinding iteratorBinding = (DCIteratorBinding) bindings1.get("studentNotEnrolledIterator");

        if (iteratorBinding != null) {
            Row currentRow = iteratorBinding.getCurrentRow();
            if (currentRow != null) {
                studentName = (String)currentRow.getAttribute("name");
                System.out.println("student is: " + currentRow.getAttribute("name"));
            }
            //queryCoursesByStudent(currentRow.getAttribute("studentId").toString());
        }
        
        return studentName;
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

    public void setStudentListPGL(RichPanelGroupLayout studentListPGL) {
        this.studentListPGL = studentListPGL;
    }

    public RichPanelGroupLayout getStudentListPGL() {
        return studentListPGL;
    }

    public void setStudentLIstPB(RichPanelBox studentLIstPB) {
        this.studentLIstPB = studentLIstPB;
    }

    public RichPanelBox getStudentLIstPB() {
        return studentLIstPB;
    }
}
