package com.tutorfind.controllers;


import com.tutorfind.model.TutorSubjectDataModel;
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
@RequestMapping("tutorsubjects")
public class TutorSubjectController {


    @Autowired
    private DataSource dataSource;


    private ArrayList<TutorSubjectDataModel> getTutorSubjectsFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM tutorsubjects");

            ArrayList<TutorSubjectDataModel> output = new ArrayList<TutorSubjectDataModel>();

            while (rs.next()) {
                output.add(new TutorSubjectDataModel(rs.getInt("tutoruserid"),rs.getInt("subjectid")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<TutorSubjectDataModel> printTutorSubject(@RequestParam(value = "tutorUserId", defaultValue = "0") int tutorUserId,
                                   @RequestParam(value = "subjectId", defaultValue = "0") int subjectId) {

        ArrayList<TutorSubjectDataModel> tutorSubjects = getTutorSubjectsFromDB();
        ArrayList<TutorSubjectDataModel> selectedTutorSubjects = new ArrayList<>();
        for (TutorSubjectDataModel tutorSubject : tutorSubjects) {
            if (tutorSubject.getTutorUserId() == tutorUserId) {
                selectedTutorSubjects.add(tutorSubject);
                return selectedTutorSubjects;
            }
            if(tutorSubject.getSubjectId() == subjectId) {
                selectedTutorSubjects.add(tutorSubject);
                return selectedTutorSubjects;
            }
        }


        if(selectedTutorSubjects.isEmpty()) {
            return tutorSubjects;
        }else {
            return selectedTutorSubjects;
        }
    }


}

