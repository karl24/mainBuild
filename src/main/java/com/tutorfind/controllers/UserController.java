package com.tutorfind.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorfind.model.UserDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
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
    public @ResponseBody ArrayList<UserDataModel> printUsers(@RequestParam(value = "userName", defaultValue = "") String userName,
                                                  @RequestParam(value = "userId", defaultValue = "0") int userId) {

        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        ArrayList<UserDataModel> acceptedUsers = new ArrayList<>();

        for(UserDataModel user : users){
            if(user.getUserName().equals(userName)) {
                acceptedUsers.add(user);

            }
            if(user.getUserId() == userId) {
                acceptedUsers.add(user);

            }
        }

        if(acceptedUsers.isEmpty()) {

            return users;
        }else {
            return acceptedUsers;
        }
    }

    @RequestMapping(value = "{studentId}/insert", method = {RequestMethod.POST})
    public UserDataModel insertUser(@PathVariable("studentId") int id, @RequestBody UserDataModel u) {

        UserDataModel user = new UserDataModel();
        user.setEmail(u.getEmail());
        user.setPasshash(u.getPasshash());
        user.setSalt(u.getSalt());
        user.setUserId(u.getUserId());
        user.setUserName(u.getUserName());
        user.setUserType(u.getUserType());

        u = user;
        updateStudentFromDB(u.getUserId(),u.getUserName(),u.getEmail(),u.getSalt(),u.getPasshash(),u.getUserType());
        return u;


    }

    public void updateStudentFromDB(int userId, String  userName, String email, String salt, String passhash, String userType){
        try (Connection connection = dataSource.getConnection()) {
            //Statement stmt = connection.createStatement();
            String query = "insert into users VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4,salt);
            preparedStatement.setString(5, passhash);
            preparedStatement.setString(6, userType);
            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }


}
