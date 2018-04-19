package com.tutorfind.junit;


import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import com.tutorfind.controllers.ForgotPasswordController;
import com.tutorfind.controllers.PostController;
import com.tutorfind.controllers.StudentController;
import com.tutorfind.model.StudentDataModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void addCourse() {

        StudentDataModel s= new StudentDataModel();
        s.setUserId(1);
        s.setActive(Boolean.TRUE);
        s.setLegalFirstName("Zeeshan");
        s.setLegalLastName("Naeem");
        s.setMajor("Major");


        HttpEntity<StudentDataModel> entity = new HttpEntity<StudentDataModel>(s, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/students/1/"),
                HttpMethod.POST, entity, String.class);

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("/students/1/"));

    }



    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
