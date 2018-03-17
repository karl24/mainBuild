package com.tutorfind.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorfind.model.UserDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private DataSource dataSource;

    private ArrayList<UserDataModel> getActiveUsersFromDB() {
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

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody UserDataModel printUsers(@RequestParam(value = "userName", defaultValue = "") String userName,
                                                  @RequestParam(value = "userId", defaultValue = "0") int userId) {

        ArrayList<UserDataModel> users = getActiveUsersFromDB();

        for(UserDataModel user : users){
            if(user.getUserName().equals(userName))
                return user;
            if(user.getUserId() == userId)
                return user;
        }

        return new UserDataModel();
    }

}
