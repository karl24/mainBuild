package com.tutorfind.junit;


import com.tutorfind.controllers.ForgotPasswordController;
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
@SpringBootTest(classes = ForgotPasswordController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ForgotPasswordTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void checkStudent() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/forgotpassword/student/abc@gmail.com/"),
                HttpMethod.GET, entity, String.class);

        String expected = "{id:Course1,name:Spring,description:10 Steps}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void checkTutor() throws JSONException {

    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange(
            createURLWithPort("/forgotpassword/tutor/abc@gmail.com/"),
            HttpMethod.GET, entity, String.class);

    String expected = "{id:Course1,name:Spring,description:10 Steps}";

    JSONAssert.assertEquals(expected, response.getBody(), false);
}


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
