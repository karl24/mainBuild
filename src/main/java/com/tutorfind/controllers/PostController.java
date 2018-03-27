/*
Created by Adam Hardy based on Karl's StudentController and UserController
 */
/*
package com.tutorfind.controllers;

import com.tutorfind.model.UserDataModel;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorfind.model.PostDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@RestController
@RequestMapping("activetutors")
public class PostController {

    @Autowired
    private DataSource dataSource;

    public ArrayList<PostDataModel> getActivePostsFromDB() {
        System.out.println("Getting active posts from DB");
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM posts WHERE active IS TRUE ORDER BY createdTs DESC");

            ArrayList<PostDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new PostDataModel(rs.getInt("postId"), rs.getString("posterType"),
                        rs.getInt("ownerId"), rs.getInt("subjectId"),
                        rs.getString("location"), rs.getString("availability"),
                        rs.getBoolean("acceptsPaid"), rs.getDouble("rate"),
                        rs.getString("unit"), rs.getTimestamp("createdTs"),
                        rs.getBoolean("active"), rs.getBoolean("acceptsGroupTutoring"),
                        rs.getInt("currentlySignedUp")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}*/

package com.tutorfind.controllers;

import com.tutorfind.model.PostDataModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@RestController
@RequestMapping("activetutors")
public class PostController{

    @Autowired
    private DataSource dataSource;

    private ArrayList<PostDataModel> getActivePostsFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM posts WHERE active IS TRUE ORDER BY createdTs DESC");

            ArrayList<PostDataModel> output = new ArrayList();

            while (rs.next()) {
                output.add(new PostDataModel(rs.getInt("postId"), rs.getString("posterType"),
                        rs.getInt("ownerId"), rs.getInt("subjectId"),
                        rs.getString("location"), rs.getString("availability"),
                        rs.getBoolean("acceptsPaid"), rs.getDouble("rate"),
                        rs.getString("unit"), rs.getTimestamp("createdTs"),
                        rs.getBoolean("active"), rs.getBoolean("acceptsGroupTutoring"),
                        rs.getInt("currentlySignedUp")));
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ArrayList<PostDataModel> printPosts() {

        ArrayList<PostDataModel> posts = getActivePostsFromDB();

        return posts;
    }

/*    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public @ResponseBody TutorsDataModel printTutor(@PathVariable("id") int id) {

        ArrayList<TutorsDataModel> tutors = getActiveTutorsFromDB();
        ArrayList<UserDataModel> users = getActiveUsersFromDB();

        for(TutorsDataModel tutor : tutors){
            for(UserDataModel user : users){
                if(tutor.getUserId() == user.getUserId()){
                    tutor.setUserType(user.getUserType());
                    tutor.setPasshash(user.getPasshash());
                    tutor.setSalt(user.getSalt());
                    tutor.setUserName(user.getUserName());
                    tutor.setEmail(user.getEmail());
                }
            }

        }

        for(TutorsDataModel tutor : tutors){
            if(tutor.getUserId() == id){
                return tutor;
            }
        }
        return null;

    }*/

}