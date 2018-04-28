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

            getUsers(rs, output);

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

    public void updateUserFromDB(int userId,String username, String email, String usertype, String[] subjects){
        try (Connection connection = dataSource.getConnection()) {
            final java.sql.Array sqlArray = connection.createArrayOf("varchar", subjects);
            String query = "update users set username = ?, email = ?,usertype = ?, subjects = ? where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,usertype);
            preparedStatement.setArray(4, sqlArray);
            preparedStatement.setInt(5,userId);

            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }
    protected static void getUsers(ResultSet rs, ArrayList<UserDataModel> output) throws SQLException {
        while (rs.next()) {
            String subjectsString = rs.getString("subjects");

            String[] subjects = subjectsString.split(",");
            subjects[0] = subjects[0].substring(1);
            subjects[subjects.length-1] = subjects[subjects.length-1].substring(0, subjects[subjects.length-1].length()-1);

            output.add(new UserDataModel(rs.getInt("userId"), rs.getString("userName"), rs.getString("email"),
                    rs.getString("salt"), rs.getString("passhash"), rs.getString("userType"),subjects));

        }
    }




    public void updatePostFromPostTable(boolean active, int id){
        try (Connection connection = dataSource.getConnection()) {
            String query = "update posts set active = ? where ownerId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(2,id);
            preparedStatement.setBoolean(1,active);
            preparedStatement.executeUpdate();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
