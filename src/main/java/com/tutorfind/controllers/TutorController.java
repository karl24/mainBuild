package com.tutorfind.controllers;


import com.tutorfind.exceptions.ResourceNotFoundException;
import com.tutorfind.model.TutorsDataModel;
import com.tutorfind.model.UserDataModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("tutors")
public class TutorController extends UserController{

    @Autowired
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(StudentController.class.getName());

    /*
    *v1 endpoints*
    GET /tutors - returns all active tutors
    GET /tutors/{id} - returns tutor based on id
    POST /tutors/login - logins tutor
    PUT /tutors - inserts tutor into DB
    POST /tutors/{tutorid} - updates tutor based on id
    POST /tutors/delete/{tutorid} - delete tutor based on id
    POST /tutors/{studentid}/addrating - student add rating to tutor

    *v2 endpoints*
    GET /tutors?status=any - returns list of tutors with any status
    GET /tutors?status=inactive - returns list of only inactive tutors accounts
    GET /tutors?status=active - returns same as '/tutors', the lust of active student accounts
    GET /tutors?name={name} - returns all tutors with given legalFirstName or legalLastName

     */


    public ArrayList<TutorsDataModel> getAllTutorsFromDB() {
        return getTutorsDataModels("select * from users inner join tutors on  users.userType = 'tutor' and users.userid = tutors.userid");

    }

    private ArrayList<TutorsDataModel> getActiveTutorsFromDB() {
        return getTutorsDataModels("select * from users inner join tutors on  users.userType = 'tutor' and users.userid = tutors.userid WHERE active = true");

    }

    protected ArrayList<TutorsDataModel> getTutorsDataModels(String s) {
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(s);

            ArrayList<TutorsDataModel> output = new ArrayList();

            setTutor(rs, output);

            return output;

        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
            return null;

        }
    }

    private void setTutor(ResultSet rs, ArrayList<TutorsDataModel> output) throws SQLException {
        while(rs.next()){
            String subjectsString = rs.getString("subjects");

            String[] subjects = subjectsString.split(",");
            subjects[0] = subjects[0].substring(1);
            subjects[subjects.length-1] = subjects[subjects.length-1].substring(0, subjects[subjects.length-1].length()-1);
            TutorsDataModel s = new TutorsDataModel(rs.getInt("userId"), rs.getString("legalfirstname"), rs.getString("legallastname"), rs.getString("bio"), rs.getString("degrees"), rs.getString("links"), rs.getString("img"), rs.getBoolean("active"), rs.getTimestamp("creationdate"), rs.getString("rating"),rs.getString("username"),rs.getString("email"),rs.getString("salt"),rs.getString("passhash"),rs.getString("usertype"),subjects);
            output.add(s);
        }
    }


    public void updateTutorsFromDB(int userId, String legalFirstName,String legalLastName, String bio, String degrees, String links,String img, boolean active, Timestamp creationdate,String rating){
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

        try (Connection connection = dataSource.getConnection()) {

           
            //Statement stmt = connection.createStatement();
            String query = "update tutors set legalFirstName = ?, legalLastName = ?, bio = ?, degrees = ?, links = ?, img = ?, active = ?, creationdate = ?, rating = CAST(? AS JSON) where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(2, legalLastName);
            preparedStatement.setString(1, legalFirstName);
            preparedStatement.setString(3, bio);
            preparedStatement.setString(4, degrees);
            preparedStatement.setString(5, links);
            preparedStatement.setString(6, img);
            preparedStatement.setBoolean(7,active);
            preparedStatement.setTimestamp(8, creationdate);
            preparedStatement.setString(9,rating);
            preparedStatement.setInt(10,userId);
            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();


        }
    }

    public void insertTutorIntoDB(int userId, String legalFirstName,String legalLastName, String bio, String degrees, String links,String img, boolean active, Timestamp timestamp, String ratings){
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

        try (Connection connection = dataSource.getConnection()) {
            //Statement stmt = connection.createStatement();
            String defaultValue = "{\"0\":\"0\"}";

            String query = "insert into tutors VALUES(?,?,?,?,?,?,?,?,?, CAST(? AS JSON))";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(3, legalLastName);
            preparedStatement.setString(2, legalFirstName);
            preparedStatement.setString(4, bio);
            preparedStatement.setString(5, degrees);
            preparedStatement.setString(6, links);
            preparedStatement.setString(7, img);
            preparedStatement.setBoolean(8,active);
            preparedStatement.setTimestamp(9, timestamp);
            preparedStatement.setString(10,defaultValue);
            preparedStatement.setInt(1,userId);

            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();


        }
    }



    public ArrayList<TutorsDataModel> getInactiveTutorsFromDB() {
        return getTutorsDataModels("select * from users inner join students on  users.userType = 'student' and users.userid = students.userid WHERE active = false");
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<TutorsDataModel> printTutorsByName(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) String status) {

        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause ResourceNotFoundException");

        ArrayList<TutorsDataModel> tutors = getActiveTutorsFromDB();


        if (name != null && !name.isEmpty()) {

            ArrayList<TutorsDataModel> acceptedTutors = new ArrayList<>();
            ArrayList<TutorsDataModel> activeTutors = getActiveTutorsFromDB();

            for (TutorsDataModel t : activeTutors) {


                if (t.getLegalFirstName().equals(name) || t.getLegalLastName().equals(name)) {
                    acceptedTutors.add(t);
                }
            }

            if (acceptedTutors.isEmpty()) {

                LOGGER.log(Level.SEVERE, "Exception occur");
                throw new ResourceNotFoundException();
            } else {

                return acceptedTutors;
            }
        } else if (status != null && !status.isEmpty()) {
            if (status.equals("active")) {
                tutors = getActiveTutorsFromDB();
                return tutors;

            } else if (status.equals("all")) {
                tutors = getAllTutorsFromDB();

                return tutors;

            } else if (status.equals("inactive")) {
                tutors = getInactiveTutorsFromDB();
                return tutors;
            }
        }


        return tutors;
    }


    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public @ResponseBody TutorsDataModel printTutor(@PathVariable("id") int id) {
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause ResourceNotFoundException");

        TutorsDataModel tutor = new TutorsDataModel();

        ArrayList<TutorsDataModel> tutors = getActiveTutorsFromDB();
        ArrayList<UserDataModel> users = getActiveUsersFromDB();

        for(TutorsDataModel t : tutors){
            for(UserDataModel user : users){
                if(t.getUserId() == user.getUserId()){
                    t.setUserType(user.getUserType());
                    t.setPasshash(user.getPasshash());
                    t.setSalt(user.getSalt());
                    t.setUserName(user.getUserName());
                    t.setEmail(user.getEmail());
                    t.setSubjects(user.getSubjects());
                }
            }

        }

        for(TutorsDataModel t : tutors){
            if(t.getUserId() == id){
                tutor = t;
            }
        }
        if(tutor.getUserName() == null) {

            LOGGER.log(Level.SEVERE, "Exception occur");
            throw new ResourceNotFoundException();
        }else {
            return tutor;
        }


    }

    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public TutorsDataModel loginStudent(@RequestBody TutorsDataModel t){
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");
        LOGGER.warning("Can cause ResourceNotFoundException");
        ArrayList<TutorsDataModel> tutors = getActiveTutorsFromDB();
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        try (Connection connection = dataSource.getConnection()) {

            String sql = "SELECT userId, passhash = crypt(?, passhash) as pass, passhash from users where passhash = crypt(?, passhash) AND username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(2,t.getPasshash());
            preparedStatement.setString(1,t.getPasshash());
            preparedStatement.setString(3,t.getUserName());
            ResultSet rs = preparedStatement.executeQuery();


            ArrayList<TutorsDataModel> output = new ArrayList<TutorsDataModel>();

            while (rs.next()) {
                for(TutorsDataModel tutor : tutors){
                    for(UserDataModel u : users){
                        if(tutor.getUserId() == u.getUserId()){
                            if(rs.getInt("userId") == tutor.getUserId()) {
                                tutor.setEmail(u.getEmail());
                                tutor.setUserName(u.getUserName());
                                tutor.setSalt(u.getSalt());
                                tutor.setUserType(u.getUserType());
                                tutor.setPasshash(u.getPasshash());
                                tutor.setSubjects(u.getSubjects());
                                output.add(tutor);

                            }
                        }

                    }

                }

            }



            if(output.isEmpty()){

                LOGGER.log(Level.SEVERE, "Exception occur");
                throw new ResourceNotFoundException();
            }else {
                return output.get(0);
            }

        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();
            return null;

        }

    }


    @RequestMapping(method = {RequestMethod.PUT})
    public ResponseEntity<TutorsDataModel> insertTutor(@RequestBody TutorsDataModel t) {
        ArrayList<UserDataModel> users = getActiveUsersFromDB();
        for(UserDataModel user : users){
            if(user.getUserName().equals(t.getUserName()) || user.getEmail().equals(t.getEmail())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        insertUserIntoDB(t.getUserName(),t.getEmail(),t.getSalt(),t.getPasshash(),t.getUserType(),t.getSubjects());
        users = getActiveUsersFromDB();
        for(UserDataModel user : users){
            if(user.getUserName().equals(t.getUserName()))
                insertTutorIntoDB(user.getUserId(),t.getLegalFirstName(),t.getLegalLastName(),t.getBio(),t.getDegrees(),t.getLinks(),t.getImg(),t.getActive(),t.getTimestamp(),t.getRating());
        }

        return new ResponseEntity<>(HttpStatus.OK);


    }

    @RequestMapping(value = "{tutorId}", method = {RequestMethod.POST})
    public ResponseEntity<TutorsDataModel> updateTutor(@PathVariable("tutorId") int id, @RequestBody TutorsDataModel t) {

        updateTutorsFromDB(id,t.getLegalFirstName(),t.getLegalLastName(),t.getBio(),t.getDegrees(),t.getLinks(),t.getImg(),t.getActive(),t.getTimestamp(),t.getRating());
        updateUserFromDB(id,t.getUserName(),t.getEmail(),t.getUserType(),t.getSubjects());
        return new ResponseEntity<>(HttpStatus.OK);


    }

    @RequestMapping(value = "delete/{tutorId}", method = {RequestMethod.POST})
    public ResponseEntity<Void> deleteTutor(@PathVariable("tutorId") int id, @RequestBody TutorsDataModel t) {

        updatePostFromPostTable(t.getActive(),id);
        updateTutorsFromDB(id,t.getActive());
        return new ResponseEntity<>(HttpStatus.OK);


    }

    public void updateTutorsFromDB(int userId, boolean active){
        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause SQLException");

        try (Connection connection = dataSource.getConnection()) {


            //Statement stmt = connection.createStatement();
            String query = "update tutors set active = ? where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(2,userId);
            preparedStatement.setBoolean(1,active);
            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Exception occur",e);
            e.printStackTrace();


        }
    }

    @RequestMapping(value = "{studentId}/addrating", method = {RequestMethod.POST})
    public ResponseEntity<Void> addRating(@PathVariable("studentId") int studentId, @RequestBody TutorsDataModel t) {

        LOGGER.info("Logger Name: "+LOGGER.getName());

        LOGGER.warning("Can cause JSONException");

        boolean badRequest = true;
        ArrayList<TutorsDataModel> tutors = getActiveTutorsFromDB();
        for( TutorsDataModel tutor : tutors) {
            if( tutor.getUserId() == t.getUserId()) {
                if (tutor.getRating() != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(tutor.getRating());
                        Iterator<?> keys = jsonObj.keys();

                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            if (key.equalsIgnoreCase(String.valueOf(studentId))) {
                                badRequest = true;
                                break;
                            } else {
                                badRequest = false;

                            }
                        }
                    } catch (JSONException e) {

                        LOGGER.log(Level.SEVERE, "Exception occur",e);
                        e.printStackTrace();
                    }
                }else {
                    badRequest = false;
                }
            }
        }
        if(badRequest == true){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            updateTutorsFromDB(t.getUserId(),t.getLegalFirstName(),t.getLegalLastName(),t.getBio(),t.getDegrees(),t.getLinks(),t.getImg(),t.getActive(),t.getTimestamp(),t.getRating());
            return new ResponseEntity<>(HttpStatus.OK);
        }


    }

}
