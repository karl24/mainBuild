/* replacing the studentPostController and tutorPostController with a combined controller and model
Ahardy based off of Karl's UserController.java
 */

package com.tutorfind.controllers;

import com.tutorfind.model.PostDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@RestController
@RequestMapping("posts")
public abstract class PostController {

    @Autowired
    private DataSource dataSource;

    public ArrayList<PostDataModel> getActivePostsFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM post WHERE active IS TRUE");

            ArrayList<PostDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new PostDataModel(rs.getInt("postId"), rs.getString("posterType"), rs.getInt("ownerId"),
                        rs.getInt("subjectId"), rs.getString("location"), rs.getBoolean("acceptsPaid"),
                        rs.getDouble("rate"), rs.getString("unit"), rs.getTimestamp("createdts"),
                        rs.getBoolean("active"), rs.getBoolean("acceptsgrouptutoring")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    PostDataModel printPostData(@RequestParam(value = "postid", defaultValue = "1") int postId,
                                          @RequestParam(value = "ownerId", defaultValue = "2") int ownerId) {

        ArrayList<PostDataModel> posts = getActivePostsFromDB();

        for (PostDataModel post : posts) {
            if (post.getPostId() == postId)
                return post;
            if(post.getOwnerId() == ownerId)
                return post;
        }


ß
        return new PostDataModel();
    }
}