package com.tutorfind.controllers;


import com.tutorfind.exceptions.ResourceNotFoundException;
import com.tutorfind.model.UserDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@RestController
@RequestMapping("admin")
public class AdminController extends UserController{

    @Autowired
    private DataSource dataSource;


    public ArrayList<UserDataModel> getAdminFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE userType = 'admin' ORDER BY creationdate DESC");

            ArrayList<UserDataModel> output = new ArrayList();

            getUsers(rs, output);

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public UserDataModel loginAdmin(@RequestBody UserDataModel u){
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        try (Connection connection = dataSource.getConnection()) {

            String sql = "SELECT * from users where passhash = crypt(?, passhash) AND username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,u.getPasshash());
            preparedStatement.setString(2,u.getUserName());


            ResultSet rs = preparedStatement.executeQuery();


            ArrayList<UserDataModel> output = new ArrayList<UserDataModel>();

            getUsers(rs,output);

            if(output.isEmpty()){
                throw new ResourceNotFoundException();
            }else {
                return output.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

    }

    @RequestMapping(value = "updateUserType/{userid}", method = {RequestMethod.POST})
    public ResponseEntity<UserDataModel> updateUserType(@PathVariable("userid") int id, @RequestBody UserDataModel u) {

        updateUserTypeFromDB(u.getUserType(),id);
        return new ResponseEntity<>(HttpStatus.OK);


    }
    public void updateUserTypeFromDB(String usertype, int userId){
        try (Connection connection = dataSource.getConnection()) {

            String query = "update users set usertype = ? where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,usertype);
            preparedStatement.setInt(2,userId);

            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }



}
