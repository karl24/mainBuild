package com.tutorfind.controllers;

import com.tutorfind.model.TutorRatingDataModel;
import com.tutorfind.model.TutorsDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@RestController
@RequestMapping("tutorrating")
public class TutorRatingController {


    @Autowired
    private DataSource dataSource;


    private ArrayList<TutorRatingDataModel> getTutorRatingsFromDB() {

        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM tutorrating");

            ArrayList<TutorRatingDataModel> output = new ArrayList<TutorRatingDataModel>();

            while (rs.next()) {
                output.add(new TutorRatingDataModel(rs.getInt("tutoruserid"),rs.getInt("studentuserid"),rs.getInt("tutorpostid"),rs.getInt("rating"),rs.getString("subject"),rs.getString("comments")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<TutorRatingDataModel> printTutorRating(@RequestParam(value = "tutoruserid", defaultValue = "0") int tutoruserid) {

        ArrayList<TutorRatingDataModel> tutorRatings = getTutorRatingsFromDB();
        ArrayList<TutorRatingDataModel> acceptedTutorRatings = new ArrayList<>();
        for (TutorRatingDataModel tutorRating : tutorRatings) {
            if (tutorRating.getTutorUserId() == tutoruserid){
                acceptedTutorRatings.add(tutorRating);

            }


        }


        if(acceptedTutorRatings.isEmpty()) {
            return tutorRatings;
        }else {
            return acceptedTutorRatings;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public ResponseEntity<Void> addRating(@PathVariable("id") int id, @RequestBody TutorsDataModel t){
        if(id == t.getUserId()) {
            updateRatingFromDB(id, t.getRatings());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



    }


    public void updateRatingFromDB(int userId, Integer[] ratings){
        try (Connection connection = dataSource.getConnection()) {
            final java.sql.Array sqlArray = connection.createArrayOf("integer", ratings);

            //Statement stmt = connection.createStatement();
            String query = "update tutors set rating = ? where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setArray(1,sqlArray);
            preparedStatement.setInt(2,userId);

            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }


}

