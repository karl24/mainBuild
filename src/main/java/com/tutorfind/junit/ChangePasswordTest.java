package com.tutorfind.junit;
import com.tutorfind.controllers.ChangePasswordController;
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
@SpringBootTest(classes = ChangePasswordController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChangePasswordTest {

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
                createURLWithPort("/changepassword/1/123"),
                HttpMethod.POST, entity, String.class);

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("/changepassword/1/123/"));

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}