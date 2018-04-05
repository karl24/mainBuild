/*
Created by Adam Hardy
 */


package com.tutorfind.controllers;

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
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("forgotpassword")
public class ForgotPasswordController {

    @Autowired
    private DataSource dataSource;

    private String isStudentEmailActive(String email){

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT u.email AS email FROM users u LEFT JOIN students s ON u.userid = s.userid WHERE u.email = ? AND s.active = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                return rs.getString("email");
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return "not a match to active student";
    }

    private String isTutorEmailActive(String email){

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT u.email AS email FROM users u LEFT JOIN tutors t ON u.userid = t.userid WHERE u.email = ? AND t.active = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                return rs.getString("email");
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return "not a match to active tutor";
    }

    //once we figure out session validation, we'll need to generate a password and hash
    private String getRandomPassword(){
        return UUID.randomUUID().toString();
    }

    private int getUserId(String email){
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT userid FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();


            if(rs.next()){
                return rs.getInt("userid");
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void updatePassword(int userId){
        String newPassword = getRandomPassword();

        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE users SET passhash = ? WHERE userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    @RequestMapping(value = "/student/{email}",method = RequestMethod.GET)
    public @ResponseBody int checkIfStudentEmailIsActive(@PathVariable("email") String email){
        if(isStudentEmailActive(email).equals(email)){
            int userId = getUserId(email);
            updatePassword(1);
            return userId;
        } else {
            return 0;
        }
    }

    @RequestMapping(value = "/tutor/{email}",method = RequestMethod.GET)
    public @ResponseBody boolean checkIfTutorEmailIsActive(@PathVariable("email") String email){
        if(isTutorEmailActive(email).equals(email)){
            int userId = getUserId(email);
            updatePassword(1);
            return true;
        } else {
            return false;
        }

    }
}
