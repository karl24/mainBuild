package com.tutorfind.junit;

import com.tutorfind.controllers.PostController;
import com.tutorfind.model.PostDataModel;
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

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PostController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();


    @Test
    public void testAllPosts(){
        testPost("/posts");
    }

    @Test
    public void testGetPostsByName(){
        testPost("/posts?name=John");
    }
    

    protected void testPost(String s2) {
        PostDataModel p = new PostDataModel();
        p.setPostId(1);
        p.setOwnerId(2);
        p.setPosterType("student");
        p.setActive(true);
        p.setLocation("NDNU");

        StudentDataModel s = new StudentDataModel();
        s.setUserId(1);
        s.setLegalFirstName("John");


        HttpEntity<PostDataModel> entity = new HttpEntity<PostDataModel>(p, headers);

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
