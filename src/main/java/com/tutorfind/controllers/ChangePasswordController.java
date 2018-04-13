package com.tutorfind.controllers;

import com.tutorfind.model.StudentDataModel;
import com.tutorfind.model.UserDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("changepassword")
public class ChangePasswordController extends UserController{


    @Autowired
    private DataSource dataSource;

    private void updatePassword(String password, int userId){
        try (Connection connection = dataSource.getConnection()) {

            String query = "UPDATE users SET passhash = crypt(?, gen_salt('bf')), salt = gen_salt('bf') WHERE userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2,userId);


            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }



    @RequestMapping(value = "{id}/{oldpassword}", method = {RequestMethod.POST})
    public ResponseEntity<Void> changePassword(@PathVariable("id") int id, @RequestBody StudentDataModel s, @PathVariable("oldpassword") String password) {
        try (Connection connection = dataSource.getConnection()) {

            String sql = "SELECT userId, passhash = crypt(?, passhash) as pass, passhash from users where passhash = crypt(?, passhash) AND userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(2, password);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(3, id);
            ResultSet rs = preparedStatement.executeQuery();



            while(rs.next()){
                ArrayList<UserDataModel> users = getActiveUsersFromDB();
                for(UserDataModel user : users){
                    if(user.getUserId() == id){
                            updatePassword(s.getPasshash(),s.getUserId());
                            return new ResponseEntity<>(HttpStatus.OK);

                    }
                }
            }





        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }



            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
