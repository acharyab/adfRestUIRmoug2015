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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

public class UpdateStudent {
    private RichInputDate studentDOB;
    private RichSelectOneRadio studentGender;
    private RichSelectOneChoice studentYear;
    private RichInputText studentName;
    private RichInputText studentId;
    private RichInputText studentGPA;
    private RichOutputFormatted studentIdList;

    public UpdateStudent() {
    }

    public String updateStudent() {
        Student newStudent = new Student();
        
        System.out.println("DOB is: " + studentDOB.getValue() + " id is: " + (String)studentIdList.getValue());
        
        newStudent.setName((String)studentName.getValue());
        //newStudent.setDob((Date)studentDOB.getValue());
        newStudent.setGender((String)studentGender.getValue());
        //newStudent.setGpa(new BigDecimal((String)studentGPA.getValue()));
        newStudent.setYear((String)studentYear.getValue());
        newStudent.setId(new BigDecimal((String)studentId.getValue()));
        newStudent.setPhoto(null);
        
        
     

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client client = Client.create(clientConfig);

        WebResource webResource = client.resource("http://127.0.0.1:7101/adfRestServiceRmoug2015-service-context-root/resources/service/student");        
        ClientResponse response = webResource.accept("application/json").type("application/json").put(ClientResponse.class, newStudent);

        if (response.getStatus() != 200) {
            FacesMessage msg = new FacesMessage("Transaction Status", "Transaction Failed : HTTP error code : " + response.getStatus());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("Student status ", msg);
        }

        if (response.getStatus() == 200) {
            FacesMessage msg = new FacesMessage("Transaction Status", "Transaction successfully completed for " + (String)studentName.getValue());

            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage("Transaction status ", msg);
        }
        return null;
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

    public void setStudentName(RichInputText studentName) {
        this.studentName = studentName;
    }

    public RichInputText getStudentName() {
        return studentName;
    }

    public void setStudentId(RichInputText studentId) {
        this.studentId = studentId;
    }

    public RichInputText getStudentId() {
        return studentId;
    }

    public void setStudentGPA(RichInputText studentGPA) {
        this.studentGPA = studentGPA;
    }

    public RichInputText getStudentGPA() {
        return studentGPA;
    }

    public void setStudentIdList(RichOutputFormatted studentIdList) {
        this.studentIdList = studentIdList;
    }

    public RichOutputFormatted getStudentIdList() {
        return studentIdList;
    }
}
