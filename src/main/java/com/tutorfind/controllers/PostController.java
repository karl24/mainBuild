/*
Created by Adam Hardy based on Karl's StudentController and UserController
 */

package com.tutorfind.controllers;

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

@RestController
@RequestMapping("posts")
public class PostController{

    @Autowired
    private DataSource dataSource;

    private ArrayList<PostDataModel> getActivePostsFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM posts WHERE active IS TRUE ORDER BY createdTs DESC");

            ArrayList<PostDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new PostDataModel(rs.getInt("postId"), rs.getString("posterType"),
                        rs.getInt("ownerId"), rs.getInt("subjectId"),
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

    public void updatePostsFromDB(int postId, String posterType, int ownerId, int subjectId, String location,
        String availability, boolean acceptsPaid, double rate, String unit, Timestamp createdTs, boolean active,
        boolean acceptsGroupTutoring, int currentlySignedUp){

        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE posts SET posterType = ?, ownerId = ?, subjectId = ?, location = ?, " +
                "availability = CAST(? AS JSON), acceptsPaid = ?, rate = ?, unit = ?, createdTs = ?, active = ?, " +
                "acceptsGroupTutoring = ?, currentlySignedUp = ? WHERE postId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, posterType);
            preparedStatement.setInt(2, ownerId);
            preparedStatement.setInt(3, subjectId);
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

            connection.close();

            ArrayList<PostDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new PostDataModel(rs.getInt("postId"), rs.getString("posterType"),
                        rs.getInt("ownerId"), rs.getInt("subjectId"),
                        rs.getString("location"), rs.getString("availability"),
                        rs.getBoolean("acceptsPaid"), rs.getDouble("rate"),
                        rs.getString("unit"), rs.getTimestamp("createdTs"),
                        rs.getBoolean("active"), rs.getBoolean("acceptsGroupTutoring"),
                        rs.getInt("currentlySignedUp")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
//            return null;
            return getActivePostsFromDB();
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

        updatePostsFromDB(id, pdm.getPosterType(), pdm.getOwnerId(), pdm.getSubjectId(),
                pdm.getLocation(), pdm.getAvailability(), pdm.isAcceptsPaid(), pdm.getRate(), pdm.getUnit(),
                pdm.getCreatedTs(), pdm.isActive(), pdm. isAcceptsGroupTutoring(), pdm.getCurrentlySignedUp());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}