package com.tutorfind;

/*
Author: Bryan
 */

import java.util.Date;
import java.util.Objects;

public class TutorsDataModel {

    // PRIMARY KEY
    private int userId;
    private String legalFirstName;
    private String legalLastName;
    private String bio;
    private String degrees;
    private String links;
    private String img;
    private Boolean active;
    private Date timestamp;
    private double avgRating;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TutorsDataModel that = (TutorsDataModel) o;
        return userId == that.userId &&
                Double.compare(that.avgRating, avgRating) == 0 &&
                Objects.equals(legalFirstName, that.legalFirstName) &&
                Objects.equals(legalLastName, that.legalLastName) &&
                Objects.equals(bio, that.bio) &&
                Objects.equals(degrees, that.degrees) &&
                Objects.equals(links, that.links) &&
                Objects.equals(img, that.img) &&
                Objects.equals(active, that.active) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, legalFirstName, legalLastName, bio, degrees, links, img, active, timestamp, avgRating);
    }

    @Override
    public String toString() {
        return "tutorsDataModel{" +
                "userId=" + userId +
                ", legalFirstName='" + legalFirstName + '\'' +
                ", legalLastName='" + legalLastName + '\'' +
                ", bio='" + bio + '\'' +
                ", degrees='" + degrees + '\'' +
                ", links='" + links + '\'' +
                ", img='" + img + '\'' +
                ", active=" + active +
                ", timestamp=" + timestamp +
                ", avgRating=" + avgRating +
                '}';
    }
}
