package com.tutorfind.controllers;

import com.tutorfind.Greeting;
import com.tutorfind.model.StudentDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("students")
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

    public void updateStudentLegalFirstName(int userId, String legalFirstName){
        try (Connection connection = dataSource.getConnection()) {
            //Statement stmt = connection.createStatement();
            String query = "update students set legalFirstName = ? where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(2,userId);
            preparedStatement.setString(1, legalFirstName);
            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    StudentDataModel printStudents(@RequestParam(value = "legalFirstName", defaultValue = "") String legalFirstName,
                                   @RequestParam(value = "userId", defaultValue = "0") int userId) {

        ArrayList<StudentDataModel> students = getStudentsFromDB();

        for (StudentDataModel student : students) {
            if (student.getLegalFirstName().equals(legalFirstName))
                return student;
            if(student.getUserId() == userId)
                return student;
        }



        return new StudentDataModel();
    }

    @RequestMapping("studentId={studentId}")
    public StudentDataModel updateStudent(@PathVariable(value = "studentId") int id, @RequestParam(value = "changeLegalFirstNameTo") String legalFirstName){

        ArrayList<StudentDataModel> students = getStudentsFromDB();

        for (StudentDataModel student : students) {

            if(student.getUserId() == id){
                updateStudentLegalFirstName(id,legalFirstName);

            }

        }
        students = getStudentsFromDB();
        for(StudentDataModel student : students) {
            if(student.getUserId() == id) {
                return student;
            }
        }



        return new StudentDataModel();
    }




}

