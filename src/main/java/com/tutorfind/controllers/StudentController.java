package com.tutorfind.controllers;

import com.tutorfind.Greeting;
import com.tutorfind.model.StudentDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class StudentController {


    @Autowired
    private DataSource dataSource;


    private ArrayList<StudentDataModel> getStudentsFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            ArrayList<StudentDataModel> output = new ArrayList<StudentDataModel>();

            while (rs.next()) {
                output.add(new StudentDataModel(rs.getInt("userId"), rs.getString("legalFirstName"), rs.getString("legalLastName"),
                        rs.getString("bio"), rs.getString("major"), rs.getString("minor"), rs.getString("img"), rs.getBoolean("active"), rs.getTimestamp("creationDate")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    @RequestMapping("/students")
    public String students(@RequestParam(value="name", defaultValue="World") String name) {

        ArrayList<StudentDataModel> students = getStudentsFromDB();
        String result = "";
        for(StudentDataModel student : students){
            result += student.toString() + "<br>";
        }

        return result;
    }
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody StudentDataModel printStudents(@RequestParam(value="legalFirstName", defaultValue="Joe") String legalFirstName) {

        ArrayList<StudentDataModel> students = getStudentsFromDB();

        for(StudentDataModel student : students){
           if(student.getLegalFirstName().equals(legalFirstName))
               return student;
        }

        return new StudentDataModel();
    }

}
