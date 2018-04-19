package com.tutorfind.junit;




import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import com.tutorfind.controllers.ForgotPasswordController;
import com.tutorfind.controllers.PostController;
import com.tutorfind.controllers.StudentPostController;
import com.tutorfind.controllers.SubjectController;
import org.json.JSONException;
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
@SpringBootTest(classes = SubjectController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubjectTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void printStudentpostTest() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/subjects/"),
                HttpMethod.GET, entity, String.class);

        String expected = "{id:Course1,name:Spring,description:10 Steps}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }



    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
