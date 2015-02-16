package com.pinnacol.view;

import com.pinnacol.model.Student;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import java.math.BigDecimal;

import java.util.Date;
import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class UpdateStudent {
    private RichInputDate studentDOB;
    private RichSelectOneRadio studentGender;
    private RichSelectOneChoice studentYear;
    
    private String studentName;
    private String studentId;
    private String studentGpa;
    private String courseId;

    public UpdateStudent() {
    }

    public String updateStudent() {
        
        Student student = new Student();
        getStudentInfo();
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date studentDB =  null;
        try {
            studentDB = df.parse(studentDOB.getValue().toString());
        } catch (ParseException e) {
        }
        
        student.setName(studentName);
        student.setDob(studentDB);
        student.setGpa(new BigDecimal(studentGpa));
        student.setGender((String)studentGender.getValue());
        student.setYear((String)studentYear.getValue());
        student.setId(new BigDecimal(studentId));

        
        
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client client = Client.create(clientConfig);

        WebResource webResource = client.resource("http://127.0.0.1:7101/adfRestServiceRmoug2015-service-context-root/resources/service/student");        
        ClientResponse response = webResource.accept("application/json").type("application/json").put(ClientResponse.class, student);

        if (response.getStatus() != 200) {
            FacesMessage msg = new FacesMessage("Transaction Status", "Update Failed : HTTP error code : " + response.getStatus());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("Student status ", msg);
        }

        if (response.getStatus() == 200) {
            queryStudents(courseId);                       
            FacesMessage msg = new FacesMessage("Transaction Status", "Record Successfully updated for " + studentName);

            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage("Transaction status ", msg);
        }
        return null;
    }
    
    
    private void getStudentInfo(){
        
        studentName = null;
        studentId   = null;
        studentGpa  = null;
        courseId    = null;
        
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iteratorBinding = (DCIteratorBinding) bindings1.get("studentsForCourseIterator");

        if (iteratorBinding != null) {
            Row currentRow = iteratorBinding.getCurrentRow();
           
            if (currentRow != null) {
                
                studentName = currentRow.getAttribute("name").toString();
                studentId   = currentRow.getAttribute("studentId").toString();
                studentGpa  = currentRow.getAttribute("gpa").toString();
                courseId    = currentRow.getAttribute("courseId").toString();
                
                System.out.println("name is: " + currentRow.getAttribute("name"));
            }
        }       
    }
    
    private void queryStudents(String id){
         BigDecimal courseId = new BigDecimal (id);
         
         BindingContainer bindings = getBindings();
         OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
         operationBinding.getParamsMap().put("courseId", courseId); 
         
         Object result = operationBinding.execute();
         ADFContext.getCurrent().getViewScope().put("queriedCourseId", courseId);
         
         if (!operationBinding.getErrors().isEmpty()) {
             System.out.println("error executing with params");
         }         
        
     }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setStudentDOB(RichInputDate studentDOB) {
        this.studentDOB = studentDOB;
    }

    public RichInputDate getStudentDOB() {
        return studentDOB;
    }

    public void setStudentGender(RichSelectOneRadio studentGender) {
        this.studentGender = studentGender;
    }

    public RichSelectOneRadio getStudentGender() {
        return studentGender;
    }

    public void setStudentYear(RichSelectOneChoice studentYear) {
        this.studentYear = studentYear;
    }

    public RichSelectOneChoice getStudentYear() {
        return studentYear;
    }

}
