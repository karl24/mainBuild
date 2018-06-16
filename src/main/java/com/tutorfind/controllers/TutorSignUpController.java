package com.tutorfind.controllers;

import com.tutorfind.model.TutorSignUpDataModel;
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

//--------------------DEPRECIATED------------------------

@RestController
@RequestMapping("tutorSignUp")
public class TutorSignUpController {

    @Autowired
    private DataSource dataSource;

    private ArrayList<TutorSignUpDataModel> getActiveTutorSignUpFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM tutorsignup");

            ArrayList<TutorSignUpDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new TutorSignUpDataModel(rs.getInt("tutorpostid"),rs.getInt("studentuserid")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ArrayList<TutorSignUpDataModel> printTutorSignups(@RequestParam(value = "tutorPostId", defaultValue = "0") int tutorPostId) {

        ArrayList<TutorSignUpDataModel> tutorSignUps = getActiveTutorSignUpFromDB();
        ArrayList<TutorSignUpDataModel> acceptedTutorSignUps = new ArrayList<>();

        for(TutorSignUpDataModel tutorSignUp : tutorSignUps){
            if(tutorSignUp.getTutorPostId() == tutorPostId){
                acceptedTutorSignUps.add(tutorSignUp);
            }
        }

        if(acceptedTutorSignUps.isEmpty()) {

            return tutorSignUps;
        }else {
            return acceptedTutorSignUps;
        }
    }

}
