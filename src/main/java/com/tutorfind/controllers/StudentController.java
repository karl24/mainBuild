package com.tutorfind.controllers;

import com.tutorfind.exceptions.ResourceNotFoundException;
import com.tutorfind.model.StudentDataModel;
import com.tutorfind.model.UserDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@CrossOrigin
@RestController
@ComponentScan("com.tutorfind.controllers.StudentRepository")
@RequestMapping("students")
public class StudentController extends UserController{


    @Autowired
    private DataSource dataSource;


    public ArrayList<StudentDataModel> getStudentsFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM students WHERE active IS TRUE ORDER BY creationdate DESC");


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

    public void updateStudentFromDB(int userId, String legalFirstName,String legalLastName, String bio, String major, String minor, String img, boolean active) {
        try (Connection connection = dataSource.getConnection()) {
            //Statement stmt = connection.createStatement();
            String query = "update students set legalFirstName = ?, legalLastName = ?, bio = ?, major = ?, minor = ?, img = ?, active = ? where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(2, legalLastName);
            preparedStatement.setString(1, legalFirstName);
            preparedStatement.setString(3, bio);
            preparedStatement.setString(4, major);
            preparedStatement.setString(5, minor);
            preparedStatement.setString(6, img);
            preparedStatement.setBoolean(7,active);

            preparedStatement.setInt(8, userId);

            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }

    public void insertStudentIntoDB(int userId, String legalFirstName,String legalLastName, String bio, String major, String minor, String img, boolean active, Timestamp creationdate){
        try (Connection connection = dataSource.getConnection()) {
            //Statement stmt = connection.createStatement();
            String query = "insert into students VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(3, legalLastName);
            preparedStatement.setString(2, legalFirstName);
            preparedStatement.setString(4, bio);
            preparedStatement.setString(5, major);
            preparedStatement.setString(6, minor);
            preparedStatement.setString(7, img);
            preparedStatement.setBoolean(8,active);
            preparedStatement.setTimestamp(9, creationdate);
            preparedStatement.setInt(1,userId);
            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<StudentDataModel> printStudents(HttpServletResponse response,
                                              @CookieValue(value = "hits",defaultValue = "0") Long hits,
                                              @RequestParam(value = "legalFirstName", defaultValue = "") String legalFirstName){

        hits++;
        Cookie cookie = new Cookie("hits",hits.toString());
        response.addCookie(cookie);

        ArrayList<StudentDataModel> students = getStudentsFromDB();
        ArrayList<StudentDataModel> acceptedStudents = new ArrayList<>();
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        for(StudentDataModel s: students){
            for(UserDataModel u : users){
                if(s.getUserId() == u.getUserId()){
                    s.setUserType(u.getUserType());
                    s.setSalt(u.getSalt());
                    s.setPasshash(u.getPasshash());
                    s.setEmail(u.getEmail());
                    s.setUserName(u.getUserName());
                }
            }
        }

        for (StudentDataModel student : students) {
            if (student.getLegalFirstName().equals(legalFirstName)){
                acceptedStudents.add(student);

            }

        }


        if (acceptedStudents.isEmpty()) {
            return students;
        }else {
            return acceptedStudents;
        }



    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody
    StudentDataModel printStudent(HttpServletResponse response, @CookieValue(value = "email", defaultValue = "") String email,
                                  @PathVariable("id") int userId) {




        ArrayList<StudentDataModel> students = getStudentsFromDB();
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        for(StudentDataModel s: students){
            for(UserDataModel u : users){
                if(s.getUserId() == u.getUserId()){
                    s.setUserType(u.getUserType());
                    s.setPasshash(u.getPasshash());
                    s.setEmail(u.getEmail());
                    s.setUserName(u.getUserName());
                    s.setSalt(u.getSalt());
                }
            }
        }

        for (StudentDataModel student : students) {


            if(student.getUserId() == userId) {
                Cookie cookie = new Cookie("email",student.getEmail());
                response.addCookie(cookie);
                return student;
            }
        }


        return null;

    }


    @RequestMapping(method = {RequestMethod.PUT}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDataModel> insertStudent(@RequestBody StudentDataModel s) {


        insertUserIntoDB(s.getUserId(),s.getUserName(),s.getEmail(),s.getSalt(),s.getPasshash(),s.getUserType());
        insertStudentIntoDB(s.getUserId(),s.getLegalFirstName(),s.getLegalLastName(),s.getBio(),s.getMajor(),s.getMinor(),s.getImg(),s.isActive(),s.getCreationDate());
        return new ResponseEntity<>(HttpStatus.OK);



    }

    @RequestMapping(value = "{studentId}", method = {RequestMethod.POST})
    public ResponseEntity<StudentDataModel> updateStudent(@PathVariable("studentId") int id, @RequestBody StudentDataModel s) {


            updateStudentFromDB(id,s.getLegalFirstName(),s.getLegalLastName(),s.getBio(),s.getMajor(),s.getMinor(),s.getImg(),s.isActive());
            updateUserFromDB(id,s.getUserName(),s.getEmail(),s.getSalt(),s.getPasshash(),s.getUserType());
            return new ResponseEntity<>(HttpStatus.OK);


    }


    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public StudentDataModel loginStudent(@RequestBody StudentDataModel s){
        ArrayList<StudentDataModel> students = getStudentsFromDB();
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        try (Connection connection = dataSource.getConnection()) {

            String sql = "SELECT username as name, passhash = crypt(?, passhash) as pass, passhash from users where passhash = crypt(?, passhash)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(2s.getPasshash()),
            preparedStatement.setString(1,s.getPasshash());
            ResultSet rs = preparedStatement.executeQuery();


            ArrayList<StudentDataModel> output = new ArrayList<StudentDataModel>();

            while (rs.next()) {
                for(StudentDataModel student : students){
                        for(UserDataModel u : users){
                            if(student.getUserId() == u.getUserId()){
                                student.setPasshash(u.getPasshash());
                                student.setEmail(u.getEmail());
                                student.setUserName(u.getUserName());
                                student.setSalt(u.getSalt());
                                student.setUserType(u.getUserType());
                                output.add(student);
                            }

                        }
                        
                }

            }



            if(output.isEmpty()){
                throw new ResourceNotFoundException();
            }else {
                return output.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

    }




}

