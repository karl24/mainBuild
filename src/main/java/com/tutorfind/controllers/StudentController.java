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


    /*
    v1 endpoints:
    GET /students - returns all active students
    GET /students/{id} - return specific student
    POST /students/delete/{id} - deletes specific student
    PUT /students - inserts new student in DB
    POST /students/{id} - updates student
    POST /students/login - logins students

    *v2 endpoints*
    GET /students/all - return all students
    GET /students/name/{name} - returns all students with given legalFirstName or legalLastName

     */

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
        setUserAttributesToStudents(students,users);

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

        StudentDataModel student = new StudentDataModel();


        ArrayList<StudentDataModel> students = getStudentsFromDB();
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        setUserAttributesToStudents(students, users);

        for (StudentDataModel s : students) {


            if(s.getUserId() == userId) {
//                Cookie cookie = new Cookie("email",student.getEmail());
//                response.addCookie(cookie);
                student = s;
            }
        }

        if (student.getUserName() == null){
            throw new ResourceNotFoundException();
        }else {

            return student;
        }

    }

    @RequestMapping(value = "name/{name}", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<StudentDataModel> printStudent(
                                  @PathVariable("name")String name) {



        ArrayList<StudentDataModel> acceptedStudents = new ArrayList<>();
        ArrayList<StudentDataModel> students = getStudentsFromDB();
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        setUserAttributesToStudents(students, users);

        for (StudentDataModel s : students) {


            if(s.getLegalFirstName().equals(name) || s.getLegalLastName().equals(name)) {
                acceptedStudents.add(s);
            }
        }

        if (acceptedStudents.isEmpty()){
            throw new ResourceNotFoundException();
        }else {

            return acceptedStudents;
        }

    }

    protected void setUserAttributesToStudents(ArrayList<StudentDataModel> students, ArrayList<UserDataModel> users) {
        for(StudentDataModel s: students){
            for(UserDataModel u : users){
                if(s.getUserId() == u.getUserId()){
                    s.setUserType(u.getUserType());
                    s.setPasshash(u.getPasshash());
                    s.setEmail(u.getEmail());
                    s.setUserName(u.getUserName());
                    s.setSalt(u.getSalt());
                    s.setSubjects(u.getSubjects());
                }
            }
        }
    }


    @RequestMapping(value = "delete/{studentId}", method = {RequestMethod.POST})
    public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") int id, @RequestBody StudentDataModel s) {

        updatePostFromPostTable(s.isActive(),id);
        updateStudentFromDB(id,s.isActive());
        return new ResponseEntity<>(HttpStatus.OK);


    }

    public void updateStudentFromDB(int userId, boolean active){
        try (Connection connection = dataSource.getConnection()) {


            //Statement stmt = connection.createStatement();
            String query = "update students set active = ? where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(2,userId);
            preparedStatement.setBoolean(1,active);
            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }

    @RequestMapping(method = {RequestMethod.PUT}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDataModel> insertStudent(@RequestBody StudentDataModel s) {
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        for(UserDataModel user : users){
            if(user.getUserName().equals(s.getUserName()) || user.getEmail().equals(s.getEmail())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        insertUserIntoDB(s.getUserName(),s.getEmail(),s.getSalt(),s.getPasshash(),s.getUserType(),s.getSubjects());
        users = getActiveUsersFromDB();
        for(UserDataModel user : users) {
            if(user.getUserName().equals(s.getUserName()))
                insertStudentIntoDB(user.getUserId(),s.getLegalFirstName(), s.getLegalLastName(), s.getBio(), s.getMajor(), s.getMinor(), s.getImg(), s.isActive(), s.getCreationDate());
        }
        return new ResponseEntity<>(HttpStatus.OK);



    }

    @RequestMapping(value = "{studentId}", method = {RequestMethod.POST})
    public ResponseEntity<StudentDataModel> updateStudent(@PathVariable("studentId") int id, @RequestBody StudentDataModel s) {

            String domainName = s.getEmail().substring(s.getEmail().length()-16);
            if(domainName.equals("student.ndnu.edu")) {
                updateStudentFromDB(id, s.getLegalFirstName(), s.getLegalLastName(), s.getBio(), s.getMajor(), s.getMinor(), s.getImg(), s.isActive());
                updateUserFromDB(id, s.getUserName(), s.getEmail(), s.getUserType(), s.getSubjects());
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }



    }

    public ArrayList<StudentDataModel> getAllStudentsFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("select * from users inner join students on  users.userType = 'student' and users.userid = students.userid");

            ArrayList<StudentDataModel> output = new ArrayList();

            while(rs.next()){
                String subjectsString = rs.getString("subjects");

                String[] subjects = subjectsString.split(",");
                subjects[0] = subjects[0].substring(1);
                subjects[subjects.length-1] = subjects[subjects.length-1].substring(0, subjects[subjects.length-1].length()-1);
                StudentDataModel s = new StudentDataModel(rs.getInt("userId"),rs.getString("legalFirstName"),rs.getString("legalLastName"), rs.getString("bio"),
                        rs.getString("major"), rs.getString("minor"), rs.getString("img"), rs.getBoolean("active"),
                        rs.getTimestamp("creationdate"),rs.getString("username"),rs.getString("email"),rs.getString("salt"),rs.getString("passhash"),rs.getString("usertype"),subjects);
                output.add(s);
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<StudentDataModel> printAllStudents(){

        return getAllStudentsFromDB();

    }


    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public StudentDataModel loginStudent(HttpServletResponse response, @CookieValue(value = "userId", defaultValue = "") String userId, @RequestBody StudentDataModel s){
        ArrayList<StudentDataModel> students = getStudentsFromDB();
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        try (Connection connection = dataSource.getConnection()) {

            String sql = "SELECT userId, passhash = crypt(?, passhash) as pass, passhash from users where passhash = crypt(?, passhash) AND username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(2,s.getPasshash());
            preparedStatement.setString(1,s.getPasshash());
            preparedStatement.setString(3,s.getUserName());
            ResultSet rs = preparedStatement.executeQuery();


            ArrayList<StudentDataModel> output = new ArrayList<StudentDataModel>();

            while (rs.next()) {
                for(StudentDataModel student : students){
                        for(UserDataModel u : users){
                            if(student.getUserId() == u.getUserId()){
                                if(rs.getInt("userId") == student.getUserId()) {
                                    student.setPasshash(u.getPasshash());
                                    student.setEmail(u.getEmail());
                                    student.setUserName(u.getUserName());
                                    student.setSalt(u.getSalt());
                                    student.setUserType(u.getUserType());
                                    student.setSubjects(u.getSubjects());
                                    output.add(student);
                                    Cookie cookie = new Cookie("userId",String.valueOf(student.getUserId()));
                                    response.addCookie(cookie);
                                }
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

