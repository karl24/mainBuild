// Built by ahardy based off of Karl's StudentController.java and https://spring.io/guides/gs/rest-service/
// Currently returns all active student posts when you hit /studentpost , takes no parameters yet
// To Do: if needed, add requests with parameteres for individual posts or subjects?  Figure out rs for JSON object activity

package com.tutorfind.controllers;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorfind.model.StudentPostDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class StudentPostController {

    @Autowired
    private DataSource dataSource;

    private ArrayList<StudentPostDataModel> getActiveStudentPostsFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

//need to add availability here and in stuendpostdatamodel.java
            ResultSet rs = stmt.executeQuery("SELECT studentPostId, ownerId, subjectId, location, acceptsPaid, rate," +
                    "unit, createdTs, active, acceptsGroupTutoring FROM studentpost WHERE active IS TRUE");

            ArrayList<StudentPostDataModel> output = new ArrayList<StudentPostDataModel>();

//currently skips availability as the spring data model does as well
            while (rs.next()) {
                output.add(new StudentPostDataModel(rs.getInt("studentPostId"), rs.getInt("ownerId"), rs.getInt("subjectId"),
                        rs.getString("location"), rs.getBoolean("acceptsPaid"), rs.getDouble("rate"),
                        rs.getString("unit"), rs.getTimestamp("createdTs"), rs.getBoolean("active"), rs.getBoolean("acceptsGroupTutoring")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    @RequestMapping("/studentpost")
    public ArrayList greeting() {
        return getActiveStudentPostsFromDB();
    }

}
