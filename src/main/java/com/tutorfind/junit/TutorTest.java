package com.tutorfind.junit;

import com.tutorfind.controllers.TutorController;
import com.tutorfind.model.TutorsDataModel;
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

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TutorController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TutorTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();


    @Test
    public void testAllTutors(){
        testTutor("/tutors");
    }

    @Test
    public void testGetTutorWithId() {

        testTutor("/tutors/1");

    }

    @Test
    public void testGetTutorWithName() {

        testTutor("/tutors?name=John");

    }

    @Test
    public void testGetTutorWithStatus() {
        testTutor("/tutors?status=active");
    }

    protected void testTutor(String s2) {
        TutorsDataModel t = new TutorsDataModel();
        t.setUserId(1);
        t.setActive(Boolean.TRUE);
        t.setLegalFirstName("John");
        t.setLegalLastName("Youssefi");
        t.setActive(true);


        HttpEntity<TutorsDataModel> entity = new HttpEntity<TutorsDataModel>(t, headers);

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
