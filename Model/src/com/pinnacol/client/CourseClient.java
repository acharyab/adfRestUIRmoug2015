package com.pinnacol.client;

import com.pinnacol.model.Course;

import java.util.ArrayList;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class CourseClient {
    public CourseClient() {
        super();
    }
    
    public List<Course> getCourseList() throws Exception{
        
        System.out.println("In the method!");
        
        
        String url = "";
        Client client = Client.create();
        List<Course> studentCourseList = new ArrayList<Course>();
        
        url = "http://127.0.0.1:7101/adfRestServiceRmoug2015-service-context-root/resources/service/course";
        System.out.println("urls is: ! " + url);
        
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        
        String json = response.getEntity(String.class);
        
        
        System.out.println("String is: " + json);
        
        ObjectMapper mapper = new ObjectMapper();
                        JSONArray jsa = new JSONArray(json);
                
                        for (int i = 0; i < jsa.length(); i++) {
                            JSONObject jo = (JSONObject) jsa.get(i);
                            studentCourseList.add(mapper.readValue(jo.toString(), Course.class));
                        }
        
        return studentCourseList;
        
    }

    public static List<Course> getCourses() throws Exception{
        
        String url = "";
        Client client = Client.create();
        List<Course> studentCourseList = new ArrayList<Course>();
        
        url = "http://127.0.0.1:7101/adfRestServiceRmoug2015-service-context-root/resources/service/course";
        System.out.println("urls is: ! " + url);
        
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        
        String json = response.getEntity(String.class);
        
        
        System.out.println("String is: " + json);
        
        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsa = new JSONArray(json);

        for (int i = 0; i < jsa.length(); i++) {
            JSONObject jo = (JSONObject) jsa.get(i);
            studentCourseList.add(mapper.readValue(jo.toString(), Course.class));
        }
        
        return studentCourseList;
        
    }

    
}
