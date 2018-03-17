package com.tutorfind.controllers;

import com.tutorfind.model.StudentDataModel;
import com.tutorfind.model.TutorPostDataModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@RestController
@RequestMapping("tutorpost")
public class TutorPostController {

    @Autowired
    private DataSource dataSource;


    private ArrayList<TutorPostDataModel> getTutorPostFromDB() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM tutorpost");

            ArrayList<TutorPostDataModel> output = new ArrayList<TutorPostDataModel>();

            while (rs.next()) {
                try {
                    output.add(new TutorPostDataModel(rs.getInt("tutorpostid"),rs.getInt("ownerId"),rs.getInt("subjectId"),rs.getString("location"),new JSONObject(rs.getString("availability")),rs.getDouble("rate"),rs.getString("unit"),rs.getTimestamp("createdts"),rs.getBoolean("active"),rs.getInt("maxgroupsize"),rs.getInt("currentsignedup"),rs.getBoolean("acceptsgroups")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    TutorPostDataModel printTutorPostData(@RequestParam(value = "tutorpostid", defaultValue = "1") int tutorPostId,
                                   @RequestParam(value = "ownerId", defaultValue = "2") int ownerId) {

        ArrayList<TutorPostDataModel> tutorPosts = getTutorPostFromDB();

        for (TutorPostDataModel tutorPost : tutorPosts) {
            if (tutorPost.getTutorpostid() == tutorPostId)
                return tutorPost;
            if(tutorPost.getOwnerId() == ownerId)
                return tutorPost;
        }



        return new TutorPostDataModel();
    }

}
