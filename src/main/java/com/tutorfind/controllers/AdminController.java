package com.tutorfind.controllers;


import com.tutorfind.exceptions.ResourceNotFoundException;
import com.tutorfind.model.UserDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController extends UserController{

    @Autowired
    private DataSource dataSource;

    private static final Logger LOGGER = Logger.getLogger(StudentController.class.getName());
    /*
    *v1 endpoints*
    POST admin/login - logins admin users
    PUT admin - inserts admin into db
    POST admin/updateUserType/{userid} - update users to admin or tutor or student
    GET admin/{id} - returns specific admin with given id
    GET admin - returns all admins
     */

    public ArrayList<UserDataModel> getAdminFromDB() {
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE userType = 'admin'");

            ArrayList<UserDataModel> output = new ArrayList();

            getUsers(rs, output);

            return output;

        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
            return null;

        }
    }



    public String getUserType(int id){
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

        try(Connection connection = dataSource.getConnection()){
            String sql = "SELECT usertype from users where userid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                return rs.getString("usertype");
            }

        }catch(SQLException e){

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public UserDataModel loginAdmin(@RequestBody UserDataModel u){
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");
        LOGGER.warning("Can cause ResourceNotFoundException");

        try (Connection connection = dataSource.getConnection()) {

            String sql = "SELECT * from users where passhash = crypt(?, passhash) AND username = ? AND usertype = 'admin'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,u.getPasshash());
            preparedStatement.setString(2,u.getUserName());


            ResultSet rs = preparedStatement.executeQuery();


            ArrayList<UserDataModel> output = new ArrayList<UserDataModel>();

            getUsers(rs,output);

            if(output.isEmpty()){

                LOGGER.log(Level.SEVERE, "Exception occur");
                throw new ResourceNotFoundException();
            }else {
                return output.get(0);
            }

        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
            return null;

        }

    }

    @RequestMapping(method = {RequestMethod.PUT}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDataModel> insertAdmin(@RequestBody UserDataModel u) {
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        for(UserDataModel user : users){
            if(user.getUserName().equals(u.getUserName()) || user.getEmail().equals(u.getEmail())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        insertUserIntoDB(u.getUserName(),u.getEmail(),u.getSalt(),u.getPasshash(),u.getUserType(),u.getSubjects());

        return new ResponseEntity<>(HttpStatus.OK);



    }

    @RequestMapping(value = "updateUserType/{userid}", method = {RequestMethod.POST})
    public ResponseEntity<UserDataModel> updateUserType(@PathVariable("userid") int id, @RequestBody UserDataModel u) {
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause IllegalArgumentException");


        if(id != u.getUserId()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                String usertype = getUserType(id);
                if (usertype.equalsIgnoreCase("student")) {
                    updateUserFromStudentTable(false, id);
                    updatePostFromPostTable(false,id);
                }
                else if (usertype.equalsIgnoreCase("tutor")) {
                    updateUserFromTutorTable(false, id);
                    updatePostFromPostTable(false,id);
                }
                else if (usertype.equalsIgnoreCase("admin")) {
                    if (u.getUserType().equalsIgnoreCase("student")) {
                        updateUserFromStudentTable(true, id);
                        updatePostFromPostTable(true,id);
                    }
                    else {
                        updateUserFromTutorTable(true, id);
                        updatePostFromPostTable(true, id);
                    }
                }
                updateUserTypeFromDB(u.getUserType(), id);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (IllegalArgumentException e) {

                LOGGER.log(Level.SEVERE, "Exception occur",e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }

    }

    public void updatePostFromPostTable(boolean active, int id){
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

        try (Connection connection = dataSource.getConnection()) {
            String query = "update posts set active = ? where ownerId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1,active);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            connection.close();
        }catch(SQLException e){

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
        }
    }

    public void updateUserFromTutorTable(boolean active, int id){
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

        try (Connection connection = dataSource.getConnection()){
            String query = "update tutors set active = ? where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1,active);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            connection.close();
        }catch(SQLException e){

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
        }
    }


    public void updateUserFromStudentTable(boolean active, int id){
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

        try (Connection connection = dataSource.getConnection()){
            String query = "update students set active = ? where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1,active);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            connection.close();
        }catch(SQLException e){

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
        }
    }




    public void updateUserTypeFromDB(String usertype, int userId){
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");
        LOGGER.warning("Can cause IllegalArgumentException");
        if(usertype.equalsIgnoreCase("admin") || usertype.equalsIgnoreCase("student") || usertype.equalsIgnoreCase("tutor")) {
            try (Connection connection = dataSource.getConnection()) {

                String query = "update users set usertype = ? where userId = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, usertype);
                preparedStatement.setInt(2, userId);

                preparedStatement.executeUpdate();
                connection.close();


            } catch (SQLException e) {

                LOGGER.log(Level.SEVERE, "Exception occur",e);
                e.printStackTrace();


            }
        }else {

            LOGGER.log(Level.SEVERE, "Exception occur");
            throw new IllegalArgumentException();
        }
    }


    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody
    UserDataModel printAdmin(@PathVariable("id") int userId) {

        ArrayList<UserDataModel> users = getAdminFromDB();

        for(UserDataModel u : users) {
            if(u.getUserId() == userId){
                return u;
            }
        }

        return null;

    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<UserDataModel> printAdmins(){

        return getAdminFromDB();

    }



}
