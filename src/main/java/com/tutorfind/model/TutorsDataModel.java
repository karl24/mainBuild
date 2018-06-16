package com.tutorfind.model;

/*
Author: Bryan
 */

import java.sql.Timestamp;
import java.util.Objects;

public class TutorsDataModel extends UserDataModel{

    // PRIMARY KEY
    private int userId;
    private String legalFirstName;
    private String legalLastName;
    private String bio;
    private String degrees;
    private String links;
    private String img;
    private Boolean active;
    private Timestamp timestamp;
    private String rating;

    public TutorsDataModel(int userId, String legalFirstName, String legalLastName, String bio, String degrees, String links, String img, Boolean active, Timestamp timestamp, String rating) {
        this.userId = userId;
        this.legalFirstName = legalFirstName;
        this.legalLastName = legalLastName;
        this.bio = bio;
        this.degrees = degrees;
        this.links = links;
        this.img = img;
        this.active = active;
        this.timestamp = timestamp;
        this.rating = rating;
    }

    public TutorsDataModel(int userId, String legalFirstName, String legalLastName, String bio, String degrees, String links, String img, Boolean active, Timestamp timestamp, String rating,,String userName, String email, String salt, String passhash, String userType, String[] subjects) {
        super(userId, userName, email, salt, passhash, userType, subjects);
        this.userId = userId;
        this.legalFirstName = legalFirstName;
        this.legalLastName = legalLastName;
        this.bio = bio;
        this.degrees = degrees;
        this.links = links;
        this.img = img;
        this.active = active;
        this.timestamp = timestamp;
        this.rating = rating;
    }

    public TutorsDataModel(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLegalFirstName() {
        return legalFirstName;
    }

    public void setLegalFirstName(String legalFirstName) {
        this.legalFirstName = legalFirstName;
    }

    public String getLegalLastName() {
        return legalLastName;
    }

    public void setLegalLastName(String legalLastName) {
        this.legalLastName = legalLastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDegrees() {
        return degrees;
    }

    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TutorsDataModel)) return false;
        if (!super.equals(o)) return false;
        TutorsDataModel that = (TutorsDataModel) o;
        return userId == that.userId &&
                Objects.equals(legalFirstName, that.legalFirstName) &&
                Objects.equals(legalLastName, that.legalLastName) &&
                Objects.equals(bio, that.bio) &&
                Objects.equals(degrees, that.degrees) &&
                Objects.equals(links, that.links) &&
                Objects.equals(img, that.img) &&
                Objects.equals(active, that.active) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), userId, legalFirstName, legalLastName, bio, degrees, links, img, active, timestamp, rating);
    }

    @Override
    public String toString() {
        return "TutorsDataModel{" + super.toString() +
                "userId=" + userId +
                ", legalFirstName='" + legalFirstName + '\'' +
                ", legalLastName='" + legalLastName + '\'' +
                ", bio='" + bio + '\'' +
                ", degrees='" + degrees + '\'' +
                ", links='" + links + '\'' +
                ", img='" + img + '\'' +
                ", active=" + active +
                ", timestamp=" + timestamp +
                ", rating=" + rating +
                '}';
    }
}
