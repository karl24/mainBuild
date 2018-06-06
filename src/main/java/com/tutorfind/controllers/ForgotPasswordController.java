/*
Created by Adam Hardy
 */


/*
Created by Adam Hardy
 */


package com.tutorfind.controllers;

import com.tutorfind.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("forgotpassword")
public class ForgotPasswordController {

    @Autowired
    private DataSource dataSource;

    String uniqueKey;
    int userId;

    private String isStudentEmailActive(String email){

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT u.email AS email FROM users u LEFT JOIN students s ON u.userid = s.userid WHERE u.email = ? AND s.active = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                return rs.getString("email");
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return "not a match to active student";
    }

    private String isTutorEmailActive(String email){

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT u.email AS email FROM users u LEFT JOIN tutors t ON u.userid = t.userid WHERE u.email = ? AND t.active = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                return rs.getString("email");
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return "not a match to active tutor";
    }

    private String getRandomPassword(){
        return UUID.randomUUID().toString();
    }

    private int getUserId(String email){
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT userid FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();


            if(rs.next()){
                return rs.getInt("userid");
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private String getFirstName(String email, String table){
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT " + table + ".legalfirstname FROM " + table + " inner join users ON " + table + ".userId = users.userId WHERE users.email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();


            if(rs.next()){
                return rs.getString("legalFirstName");
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String updatePassword(int userId){
        String newPassword = getRandomPassword();

        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE users SET passhash = crypt(?, gen_salt('bf')), salt = gen_salt('bf') WHERE userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
            connection.close();
            return newPassword;


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return newPassword;
    }

    private  ResponseEntity<Void> sendMail(String email, String table) {
        final String username = "tutorfindapp@gmail.com";
        final String password = "y2xzIhJQzk2g";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tutorfindapp@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("New Password");
            message.setText("Dear " + getFirstName(email,table) + ", "
                    + "\n\n Go to this link to reset password: https://www.tutor-find.me/resetpassword/" + uniqueKey);

            Transport.send(message);

//            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{uniquekey}",method = RequestMethod.GET)
    public String getNewPassword(@PathVariable("uniquekey") String uniquekey){
        String newPassword;
        if(uniquekey.equals(uniqueKey)){
            newPassword = updatePassword(userId);
            return newPassword;
        }else {
           throw new ResourceNotFoundException();
        }


    }


    private String getUniqueKey(){

        return UUID.randomUUID().toString();
    }


    @RequestMapping(value = "/student/{email}",method = RequestMethod.GET)
    public ResponseEntity<Void> checkIfStudentEmailIsActive(HttpServletResponse response, @CookieValue(value = "uniqueKey", defaultValue = "") String unique, @PathVariable("email") String email){
        if(isStudentEmailActive(email).equals(email)){
            userId = getUserId(email);
            uniqueKey = getUniqueKey();
            Cookie cookie = new Cookie("uniqueKey",uniqueKey);
            response.addCookie(cookie);
            //String newPassword = updatePassword(userId);
            sendMail(email,"students");
            HttpHeaders headers  = new HttpHeaders();
            headers.add(cookie.getName(),cookie.getValue());
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/tutor/{email}",method = RequestMethod.GET)
    public ResponseEntity<Void> checkIfTutorEmailIsActive(HttpServletResponse response, @CookieValue(value = "uniqueKey", defaultValue = "") String unique,@PathVariable("email") String email){
        if(isTutorEmailActive(email).equals(email)){
            uniqueKey = getUniqueKey();
            userId = getUserId(email);
            Cookie cookie = new Cookie("uniqueKey",uniqueKey);
            response.addCookie(cookie);
            sendMail(email, "tutors");
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

    }
}
