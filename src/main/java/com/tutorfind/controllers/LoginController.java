package com.tutorfind.controllers;

import com.tutorfind.exceptions.ResourceNotFoundException;
import com.tutorfind.model.StudentDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;


@RestController
@RequestMapping("login")
public class LoginController {


    @Autowired
    private DataSource dataSource;

    private StudentController studentController = new StudentController();


    @RequestMapping(method = {RequestMethod.POST})
    public StudentDataModel loginStudent(@RequestBody StudentDataModel s){
        ArrayList<StudentDataModel> students = studentController.getStudentsFromDB();
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT userId, username, passhash FROM users WHERE userName = ?");
            preparedStatement.setString(1,s.getUserName());
            ResultSet rs = preparedStatement.executeQuery();


            ArrayList<StudentDataModel> output = new ArrayList<StudentDataModel>();

            while (rs.next()) {
                for(StudentDataModel student : students){
                    if(rs.getInt("userId") == student.getUserId());
                    output.add(new StudentDataModel());
                }

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
