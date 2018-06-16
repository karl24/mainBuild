package com.tutorfind.controllers;


import com.tutorfind.model.SubjectDataModel;
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
@RequestMapping("subjects")
public class SubjectController {

    @Autowired
    private DataSource dataSource;

    private ArrayList<SubjectDataModel> getActiveSubjectFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM subject");

            ArrayList<SubjectDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new SubjectDataModel(rs.getInt("subjectid"),rs.getString("subjectname")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }



    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ArrayList<SubjectDataModel> printSubjects(@RequestParam(value = "subjectId", defaultValue = "0") int subjectId) {

        ArrayList<SubjectDataModel> subjects = getActiveSubjectFromDB();
        ArrayList<SubjectDataModel> acceptedSubjects = new ArrayList<>();

        for(SubjectDataModel subject : subjects){
            if(subject.getSubjectId() == subjectId) {
                acceptedSubjects.add(subject);
            }
        }

        if(acceptedSubjects.isEmpty()) {
            return subjects;
        }else {
            return acceptedSubjects;
        }
    }

}
