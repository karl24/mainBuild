/*
Created by Adam Hardy based on Karl's StudentController and UserController
 */

package com.tutorfind.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorfind.model.PostDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@RestController
@RequestMapping("posts")
public class PostController {

    @Autowired
    private DataSource dataSource;

    public ArrayList<PostDataModel> getActivePostsFromDB() {
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
}