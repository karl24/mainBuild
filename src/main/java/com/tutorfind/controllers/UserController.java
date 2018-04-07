package com.tutorfind.controllers;

import com.tutorfind.model.UserDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

@RestController
public abstract class UserController {

    @Autowired
    private DataSource dataSource;


    private static final Random RANDOM = new SecureRandom();

    public ArrayList<UserDataModel> getActiveUsersFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            ArrayList<UserDataModel> output = new ArrayList();

            while (rs.next()) {
                String subjectsString = rs.getString("subjects");

                    String[] r = subjectsString.split(",");
                    String[] subjects = new String[r.length];
                    for (int i = 0; i < r.length; i++) {
                        if (i == 0) {
                            subjects[i] = r[i].substring(1);
                        } else if (i == r.length - 1) {
                            subjects[i] = r[i].substring(0, 1);
                        } else {
                            subjects[i] = r[i];
                        }
                    }
                    output.add(new UserDataModel(rs.getInt("userId"), rs.getString("userName"), rs.getString("email"),
                            rs.getString("salt"), rs.getString("passhash"), rs.getString("userType"),r));

            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }



    public void insertUserIntoDB(String  userName, String email, String salt, String passhash, String userType, String[] subjects){
        try (Connection connection = dataSource.getConnection()) {
            final java.sql.Array sqlArray = connection.createArrayOf("VARCHAR", subjects);
            String query = "insert into users VALUES (DEFAULT,?,?,gen_salt('bf'),crypt(?,gen_salt('bf')),?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, passhash);
            preparedStatement.setString(4, userType);
            preparedStatement.setArray(5,sqlArray);
            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void updateUserFromDB(int userId,String username, String email, String salt, String passhash, String usertype, String[] subjects){
        try (Connection connection = dataSource.getConnection()) {
            final java.sql.Array sqlArray = connection.createArrayOf("varchar", subjects);
            String query = "update users set username = ?, email = ?, salt = ?, passhash = ?, usertype = ?, subjects = ? where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,salt);
            preparedStatement.setString(4,passhash);
            preparedStatement.setString(5,usertype);
            preparedStatement.setArray(6, sqlArray);
            preparedStatement.setInt(7,userId);

            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }


}
