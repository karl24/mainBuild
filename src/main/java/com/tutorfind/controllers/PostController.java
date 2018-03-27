/*
Created by Adam Hardy based on Karl's StudentController and UserController
 */

package com.tutorfind.controllers;

import com.tutorfind.model.UserDataModel;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorfind.model.PostDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@RestController
@RequestMapping("activetutors")
public class PostController {
/*
    @Autowired
    private DataSource dataSource;

    public ArrayList<PostDataModel> getActivePostsFromDB() {
        System.out.println("Getting active posts from DB");
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
    }*/

    @Autowired
    private DataSource dataSource;

    public ArrayList<UserDataModel> getActiveUsersFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            ArrayList<UserDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new UserDataModel(rs.getInt("userId"), rs.getString("userName"), rs.getString("email"),
                        rs.getString("salt"), rs.getString("passhash"), rs.getString("userType")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

}