/*
Created by Adam Hardy based on Karl's StudentController and UserController
 */

package com.tutorfind.controllers;

import com.tutorfind.exceptions.ResourceNotFoundException;
import com.tutorfind.model.PostDataModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("posts")
public class PostController{

    @Autowired
    private DataSource dataSource;

    /*
     *v1 endpoints*
     GET /posts?={type} - returns all posts based on the type
     GET /posts/subject/{subject} - returns specific posts based on subjects
     GET /posts/{id} - returns posts based on id
     POST /posts/{id} - updates posts based on id
     PUT /posts - inserts post into DB
     */

    private ArrayList<PostDataModel> getActivePostsFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM posts WHERE active IS TRUE ORDER BY createdTs DESC");

            ArrayList<PostDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new PostDataModel(rs.getInt("postId"), rs.getString("posterType"),
                        rs.getInt("ownerId"), rs.getString("subject"),
                        rs.getString("location"), rs.getString("availability"),
                        rs.getBoolean("acceptsPaid"), rs.getDouble("rate"),
                        rs.getString("unit"), rs.getTimestamp("createdTs"),
                        rs.getBoolean("active"), rs.getBoolean("acceptsGroupTutoring"),
                        rs.getInt("currentlySignedUp")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updatePostsFromDB(int postId, String posterType, int ownerId, String subject, String location,
        String availability, boolean acceptsPaid, double rate, String unit, Timestamp createdTs, boolean active,
        boolean acceptsGroupTutoring, int currentlySignedUp){

        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE posts SET posterType = ?, ownerId = ?, subject = ?, location = ?, " +
                "availability = CAST(? AS JSON), acceptsPaid = ?, rate = ?, unit = ?, createdTs = ?, active = ?, " +
                "acceptsGroupTutoring = ?, currentlySignedUp = ? WHERE postId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, posterType);
            preparedStatement.setInt(2, ownerId);
            preparedStatement.setString(3, subject);
            preparedStatement.setString(4, location);
            preparedStatement.setString(5, availability);
            preparedStatement.setBoolean(6, acceptsPaid);
            preparedStatement.setDouble(7, rate);
            preparedStatement.setString(8, unit);
            preparedStatement.setTimestamp(9, createdTs);
            preparedStatement.setBoolean(10, active);
            preparedStatement.setBoolean(11, acceptsGroupTutoring);
            preparedStatement.setInt(12, currentlySignedUp);
            preparedStatement.setInt(13, postId);

            preparedStatement.executeUpdate();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<PostDataModel> getActivePostsByTypeFromDB(String posterType) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM posts WHERE active IS TRUE AND posterType=? ORDER BY createdTs DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, posterType);

            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<PostDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new PostDataModel(rs.getInt("postId"), rs.getString("posterType"),
                        rs.getInt("ownerId"), rs.getString("subject"),
                        rs.getString("location"), rs.getString("availability"),
                        rs.getBoolean("acceptsPaid"), rs.getDouble("rate"),
                        rs.getString("unit"), rs.getTimestamp("createdTs"),
                        rs.getBoolean("active"), rs.getBoolean("acceptsGroupTutoring"),
                        rs.getInt("currentlySignedUp")));
            }

            connection.close();
            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertPostIntoDB(String posterType, int ownerId, String subject, String location,
        String availability, boolean acceptsPaid, double rate, String unit, Timestamp createdTs, boolean active,
        boolean acceptsGroupTutoring, int currentlySignedUp){

        try (Connection connection = dataSource.getConnection()) {

            String query = "INSERT INTO posts (posterType, ownerId, subject, location, availability, acceptsPaid, " +
                "rate, unit, createdTs, active, acceptsGroupTutoring, currentlySignedUp) " +
                "VALUES(?,?,?,?,CAST(? AS JSON),?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, posterType);
            preparedStatement.setInt(2, ownerId);
            preparedStatement.setString(3, subject);
            preparedStatement.setString(4, location);
            preparedStatement.setString(5, availability);
            preparedStatement.setBoolean(6, acceptsPaid);
            preparedStatement.setDouble(7, rate);
            preparedStatement.setString(8, unit);
            preparedStatement.setTimestamp(9, createdTs);
            preparedStatement.setBoolean(10, active);
            preparedStatement.setBoolean(11, acceptsGroupTutoring);
            preparedStatement.setInt(12, currentlySignedUp);


            preparedStatement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    //working with empty param or no param, doesn't filter correctly when I put in tutor or student
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ArrayList<PostDataModel> printPosts(@RequestParam(value = "type", required = false
        ) String posterType) {

        if(posterType != null && !posterType.isEmpty()){
            return getActivePostsByTypeFromDB(posterType);
        } else {
            return getActivePostsFromDB();
        }
    }

    @RequestMapping(value = "subject/{subject}",method = RequestMethod.GET)
    public @ResponseBody ArrayList<PostDataModel> getPostsBasedOnSubjects(@PathVariable("subject") ArrayList<String> subject) {
        ArrayList<PostDataModel> posts = getActivePostsFromDB();
        ArrayList<PostDataModel> acceptedPosts = new ArrayList<>();
        for(PostDataModel post: posts) {
            for(String s : subject) {
                if (post.getSubject().equalsIgnoreCase(s)) {
                    acceptedPosts.add(post);
                }

            }
        }
        if(acceptedPosts.isEmpty()){
            throw new ResourceNotFoundException();
        }else {
            return acceptedPosts;
        }
    }

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public @ResponseBody PostDataModel printPosts(@PathVariable("id") int id) {

        ArrayList<PostDataModel> posts = getActivePostsFromDB();

        for(PostDataModel post : posts){
            if(post.getPostId() == id){
                return post;
            }
        }
        return null;
    }

    @RequestMapping(value = "{postId}", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDataModel> updatePost(@PathVariable("postId") int id, @RequestBody PostDataModel pdm) {

        updatePostsFromDB(id, pdm.getPosterType(), pdm.getOwnerId(), pdm.getSubject(),
                pdm.getLocation(), pdm.getAvailability(), pdm.isAcceptsPaid(), pdm.getRate(), pdm.getUnit(),
                pdm.getCreatedTs(), pdm.isActive(), pdm. isAcceptsGroupTutoring(), pdm.getCurrentlySignedUp());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = {RequestMethod.PUT}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDataModel> insertPost(@RequestBody PostDataModel pdm) {

        insertPostIntoDB(pdm.getPosterType(), pdm.getOwnerId(), pdm.getSubject(),
                pdm.getLocation(), pdm.getAvailability(), pdm.isAcceptsPaid(), pdm.getRate(), pdm.getUnit(),
                pdm.getCreatedTs(), pdm.isActive(), pdm. isAcceptsGroupTutoring(), pdm.getCurrentlySignedUp());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}