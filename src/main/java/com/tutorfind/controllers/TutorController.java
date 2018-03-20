package com.tutorfind.controllers;


import com.tutorfind.model.TutorsDataModel;
import com.tutorfind.model.UserDataModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@RestController
@RequestMapping("tutors")
public class TutorController extends UserController{

    @Autowired
    private DataSource dataSource;

    private ArrayList<TutorsDataModel> getActiveTutorsFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM tutors");

            ArrayList<TutorsDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new TutorsDataModel(rs.getInt("userId"),rs.getString("legalfirstname"),rs.getString("legallastname"),rs.getString("bio"),rs.getString("degrees"),rs.getString("links"),rs.getString("img"),rs.getBoolean("active"),rs.getTimestamp("creationdate"),rs.getInt("avgrating")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    public void updateTutorsFromDB(int userId, String legalFirstName,String legalLastName, String bio, String degrees, String links,String img, boolean active, Timestamp creationdate, double avgRating){
        try (Connection connection = dataSource.getConnection()) {
            //Statement stmt = connection.createStatement();
            String query = "update tutors set legalFirstName = ?, legalLastName = ?, bio = ?, degrees = ?, links = ?, img = ?, active = ?, creationdate = ?, avgrating = ? where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(2, legalLastName);
            preparedStatement.setString(1, legalFirstName);
            preparedStatement.setString(3, bio);
            preparedStatement.setString(4, degrees);
            preparedStatement.setString(5, links);
            preparedStatement.setString(6, img);
            preparedStatement.setBoolean(7,active);
            preparedStatement.setTimestamp(8, creationdate);
            preparedStatement.setDouble(9,avgRating);
            preparedStatement.setInt(10,userId);
            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }

    public void insertTutorIntoDB(int userId, String legalFirstName,String legalLastName, String bio, String degrees, String links,String img, boolean active, Timestamp timestamp, double avgRating){
        try (Connection connection = dataSource.getConnection()) {
            //Statement stmt = connection.createStatement();
            String query = "insert into students VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(3, legalLastName);
            preparedStatement.setString(2, legalFirstName);
            preparedStatement.setString(4, bio);
            preparedStatement.setString(5, degrees);
            preparedStatement.setString(6, links);
            preparedStatement.setString(7, img);
            preparedStatement.setBoolean(8,active);
            preparedStatement.setTimestamp(9, timestamp);
            preparedStatement.setDouble(10,avgRating);
            preparedStatement.setInt(1,userId);
            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ArrayList<TutorsDataModel> printTutors(@RequestParam(value = "userId", defaultValue = "0") int userId) {

        ArrayList<TutorsDataModel> tutors = getActiveTutorsFromDB();
        ArrayList<TutorsDataModel> acceptedTutors = new ArrayList<>();
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        for(TutorsDataModel tutor : tutors){
            for(UserDataModel user : users){
                if(tutor.getUserId() == user.getUserId()){
                    tutor.setUserType(user.getUserType());
                    tutor.setPasshash(user.getPasshash());
                    tutor.setEmail(user.getEmail());
                    tutor.setSalt(user.getSalt());
                    tutor.setUserName(user.getUserName());
                }
            }

        }

        for(TutorsDataModel tutor : tutors){
            if(tutor.getUserId() == userId) {
                acceptedTutors.add(tutor);
            }
        }

        if(acceptedTutors.isEmpty()) {

            return tutors;
        }else {
            return acceptedTutors;
        }
    }

    @RequestMapping(value = "insert", method = {RequestMethod.POST})
    public ResponseEntity<TutorsDataModel> insertTutor(@RequestBody TutorsDataModel t) {

        TutorsDataModel tutor = new TutorsDataModel();
        tutor.setLegalFirstName(t.getLegalFirstName());
        tutor.setLegalLastName(t.getLegalLastName());
        tutor.setActive(t.getActive());
        tutor.setAvgRating(t.getAvgRating());
        tutor.setBio(t.getBio());
        tutor.setDegrees(t.getDegrees());
        tutor.setImg(t.getImg());
        tutor.setLinks(t.getLinks());
        tutor.setTimestamp(t.getTimestamp());
        tutor.setUserId(t.getUserId());
        tutor.setEmail(t.getEmail());
        tutor.setPasshash(t.getPasshash());
        tutor.setSalt(t.getSalt());
        tutor.setUserName(t.getUserName());
        tutor.setUserType(t.getUserType());

        t = tutor;
        insertUserIntoDB(t.getUserId(),t.getUserName(),t.getEmail(),t.getSalt(),t.getPasshash(),t.getUserType());
        insertTutorIntoDB(t.getUserId(),t.getLegalFirstName(),t.getLegalLastName(),t.getBio(),t.getDegrees(),t.getLinks(),t.getImg(),t.getActive(),t.getTimestamp(),t.getAvgRating());
        return new ResponseEntity<>(HttpStatus.OK);


    }

    @RequestMapping(value = "update/{tutorId}", method = {RequestMethod.POST})
    public ResponseEntity<TutorsDataModel> updateTutor(@PathVariable("tutorId") int id, @RequestBody TutorsDataModel t) {

        TutorsDataModel tutor = new TutorsDataModel();
        tutor.setLegalFirstName(t.getLegalFirstName());
        tutor.setLegalLastName(t.getLegalLastName());
        tutor.setActive(t.getActive());
        tutor.setAvgRating(t.getAvgRating());
        tutor.setBio(t.getBio());
        tutor.setDegrees(t.getDegrees());
        tutor.setImg(t.getImg());
        tutor.setLinks(t.getLinks());
        tutor.setTimestamp(t.getTimestamp());
        tutor.setUserId(t.getUserId());
        tutor.setEmail(t.getEmail());
        tutor.setPasshash(t.getPasshash());
        tutor.setSalt(t.getSalt());
        tutor.setUserName(t.getUserName());
        tutor.setUserType(t.getUserType());

        t = tutor;
        updateTutorsFromDB(id,t.getLegalFirstName(),t.getLegalLastName(),t.getBio(),t.getDegrees(),t.getLinks(),t.getImg(),t.getActive(),t.getTimestamp(),t.getAvgRating());
        updateUserFromDB(id,t.getUserName(),t.getEmail(),t.getSalt(),t.getPasshash(),t.getUserType());
        return new ResponseEntity<>(HttpStatus.OK);


    }

}
