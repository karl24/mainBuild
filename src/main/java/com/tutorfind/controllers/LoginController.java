package com.tutorfind.controllers;

import com.tutorfind.exceptions.ResourceNotFoundException;
import com.tutorfind.model.StudentDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


@RestController
@RequestMapping("login")
public class LoginController {


    @Autowired
    private DataSource dataSource;


    @RequestMapping(method = {RequestMethod.POST})
    public StudentDataModel loginStudent(@RequestBody StudentDataModel s){

        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT username, passhash FROM users WHERE \"username\" = " + s.getUserName());


            ArrayList<StudentDataModel> output = new ArrayList<StudentDataModel>();

            while (rs.next()) {
                output.add(new StudentDataModel(rs.getInt("userId"), rs.getString("legalFirstName"), rs.getString("legalLastName"),
                        rs.getString("bio"), rs.getString("major"), rs.getString("minor"), rs.getString("img"), rs.getBoolean("active"), rs.getTimestamp("creationDate")));
            }

           if(output.isEmpty()){
                throw new ResourceNotFoundException();
           }else {
                System.out.println(output.get(0));
                return output.get(0);
           }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

    }






}
