package com.tutorfind.controllers;

import com.tutorfind.Greeting;
import com.tutorfind.model.StudentDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentController {


    @Autowired
    private DataSource dataSource;


    @RequestMapping("/student")
    public String student(@RequestParam(value="name", defaultValue="World") String name) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            // stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
            //stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            ArrayList<StudentDataModel> output = new ArrayList<StudentDataModel>();

            while (rs.next()) {
                output.add(new StudentDataModel(rs.getInt("userId"),rs.getString("legalFirstName"),rs.getString("legalLastName"),
                        rs.getString("bio"),rs.getString("major"),rs.getString("minor"),rs.getString("img"),rs.getBoolean("active"),rs.getTimestamp("creationDate")));
            }
            String result = "";

            for(StudentDataModel cust : output){
                result += cust.toString() + "<br>";
            }

            return result;
    } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
    }
