package com.tutorfind.junit;


import com.tutorfind.controllers.StudentController;
import com.tutorfind.model.StudentDataModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testGetStudentWithId() {

        testStudent("/students/1/");

    }

    @Test
    public void testGetStudentWithName() {

        testStudent("/students/name/John");

    }

    @Test
    public void testAllStudents(){
        testStudent("/students");
    }


    protected void testStudent(String s2) {
        StudentDataModel s= new StudentDataModel();
        s.setUserId(1);
        s.setActive(Boolean.TRUE);
        s.setLegalFirstName("John");
        s.setLegalLastName("Youssefi");
        s.setMajor("CIS");


        HttpEntity<StudentDataModel> entity = new HttpEntity<StudentDataModel>(s, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(s2),
                HttpMethod.POST, entity, String.class);

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains(s2));
    }




    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
