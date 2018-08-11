/*
Created by Adam Hardy based on Karl's StudentController and UserController
 */

package com.tutorfind.controllers;

import com.tutorfind.exceptions.ResourceNotFoundException;
import com.tutorfind.model.PostDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("posts")
public class PostController{

    @Autowired
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(StudentController.class.getName());
    /*
     *v1 endpoints*
     GET /posts?type={type} - returns all posts based on the type
     GET /posts/subject/{subject} - returns specific posts based on subjects
     GET /posts/{id} - returns posts based on id
     POST /posts/{id} - updates posts based on id
     PUT /posts - inserts post into DB

     *v2 endpoints*
     Get /posts?name={name} - returns all posts on given first name or last name
     */

    private ArrayList<PostDataModel> getActivePostsFromDB() {
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

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

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
            return null;
        }
    }

    public void updatePostsFromDB(int postId, String posterType, int ownerId, String subject, String location,
        String availability, boolean acceptsPaid, double rate, String unit, Timestamp createdTs, boolean active,
        boolean acceptsGroupTutoring, int currentlySignedUp){
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");


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

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
        }
    }

    private ArrayList<PostDataModel> getActivePostsByOwnerNameFromDB(String name) {
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

        try (Connection connection = dataSource.getConnection()) {
            String query = "select * from posts inner join tutors on posts.ownerid = tutors.userid AND (tutors.legalfirstname = ? OR tutors.legallastname = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,name);

            String query2 = "select * from posts inner join students on posts.ownerid = students.userid AND (students.legalfirstname = ? OR students.legallastname = ?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement2.setString(1, name);
            preparedStatement2.setString(2,name);


            ResultSet rs = preparedStatement.executeQuery();

            ResultSet rs2 = preparedStatement2.executeQuery();

            ArrayList<PostDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new PostDataModel(rs.getInt("postId"), rs.getString("posterType"),
                        rs.getInt("ownerId"), rs.getString("subject"),
                        rs.getString("location"), rs.getString("availability"),
                        rs.getBoolean("acceptsPaid"), rs.getDouble("rate"),
                        rs.getString("unit"), rs.getTimestamp("createdTs"),
                        rs.getBoolean("active"), rs.getBoolean("acceptsGroupTutoring"),
                        rs.getInt("currentlySignedUp")));
                output.add(new PostDataModel(rs2.getInt("postId"), rs2.getString("posterType"),
                        rs2.getInt("ownerId"), rs2.getString("subject"),
                        rs2.getString("location"), rs2.getString("availability"),
                        rs2.getBoolean("acceptsPaid"), rs2.getDouble("rate"),
                        rs2.getString("unit"), rs2.getTimestamp("createdTs"),
                        rs2.getBoolean("active"), rs2.getBoolean("acceptsGroupTutoring"),
                        rs2.getInt("currentlySignedUp")));
            }

            connection.close();
            return output;

        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<PostDataModel> getActivePostsByTypeFromDB(String posterType) {
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

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

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
            return null;
        }
    }

    public void insertPostIntoDB(String posterType, int ownerId, String subject, String location,
        String availability, boolean acceptsPaid, double rate, String unit, Timestamp createdTs, boolean active,
        boolean acceptsGroupTutoring, int currentlySignedUp){
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");


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

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();

        }
    }


    //working with empty param or no param, doesn't filter correctly when I put in tutor or student
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ArrayList<PostDataModel> printPosts(@RequestParam(value = "type", required = false
        ) String posterType, @RequestParam(value = "name", required = false) String name) {
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause ResourceNotFoundException");

        if(name != null && !name.isEmpty()) {
            ArrayList<PostDataModel> posts = getActivePostsByOwnerNameFromDB(name);

            if(posts.isEmpty()){

                LOGGER.log(Level.SEVERE, "Exception occur");
                throw new ResourceNotFoundException();
            }else {
                return posts;
            }
        }

        if(posterType != null && !posterType.isEmpty()){
            return getActivePostsByTypeFromDB(posterType);
        } else {
            return getActivePostsFromDB();
        }


    }

    @RequestMapping(value = "subject/{subject}",method = RequestMethod.GET)
    public @ResponseBody ArrayList<PostDataModel> getPostsBasedOnSubjects(@PathVariable("subject") ArrayList<String> subject) {
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

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

            LOGGER.log(Level.SEVERE, "Exception occur");
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