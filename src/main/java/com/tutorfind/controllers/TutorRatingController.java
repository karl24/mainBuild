package com.tutorfind.controllers;

import com.tutorfind.model.TutorRatingDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    TutorRatingDataModel printTutorRating(@RequestParam(value = "tutoruserid", defaultValue = "0") int tutoruserid) {

        ArrayList<TutorRatingDataModel> tutorRatings = getTutorRatingsFromDB();

        for (TutorRatingDataModel tutorRating : tutorRatings) {
            if (tutorRating.getTutorUserId() == tutoruserid)
                return tutorRating;

        }



        return new TutorRatingDataModel();
    }


}

