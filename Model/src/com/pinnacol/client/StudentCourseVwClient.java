package com.pinnacol.client;

import com.pinnacol.model.StudentCourseVw;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class StudentCourseVwClient {
    public StudentCourseVwClient() {
        super();
    }
    public List<StudentCourseVw> getStudents() throws Exception {
        String url = "";
        Client client = Client.create();
        List<StudentCourseVw> studentCourseList = new ArrayList<StudentCourseVw>();
        
        url = "http://127.0.0.1:7101/adfRestServiceRmoug2015-service-context-root/resources/service/studentCourse?courseId=20";
        System.out.println("urls is: ! " + url);

        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

        String json = response.getEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsa = new JSONArray(json);

        for (int i = 0; i < jsa.length(); i++) {
            JSONObject jo = (JSONObject) jsa.get(i);
            studentCourseList.add(mapper.readValue(jo.toString(), StudentCourseVw.class));
        }
        return studentCourseList;
    }      
    public List<StudentCourseVw> getStudentsForCourse(BigDecimal courseId) throws Exception {
        String url = "";
        Client client = Client.create();
        List<StudentCourseVw> studentCourseList = new ArrayList<StudentCourseVw>();
        
        url = "http://127.0.0.1:7101/adfRestServiceRmoug2015-service-context-root/resources/service/studentCourse?courseId=" + courseId;
        System.out.println("urls is: ! " + url);

        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

        String json = response.getEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsa = new JSONArray(json);

        for (int i = 0; i < jsa.length(); i++) {
            JSONObject jo = (JSONObject) jsa.get(i);
            studentCourseList.add(mapper.readValue(jo.toString(), StudentCourseVw.class));
        }
        return studentCourseList;
    }    
}
