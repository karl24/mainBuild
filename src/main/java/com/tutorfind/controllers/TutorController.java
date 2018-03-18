package com.tutorfind.controllers;


import com.tutorfind.model.TutorsDataModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@RestController
@RequestMapping("tutors")
public class TutorController {

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

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ArrayList<TutorsDataModel> printTutors(@RequestParam(value = "userId", defaultValue = "0") int userId) {

        ArrayList<TutorsDataModel> tutors = getActiveTutorsFromDB();
        ArrayList<TutorsDataModel> acceptedTutors = new ArrayList<>();

        for(TutorsDataModel tutor : tutors){
          if(tutor.getUserId() == userId){
              tutors.add(tutor);
          }
        }

        if(acceptedTutors.isEmpty()) {

            return tutors;
        }else {
            return acceptedTutors;
        }
    }

}
