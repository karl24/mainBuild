package com.tutorfind.model;

import java.sql.Timestamp;
import java.util.Objects;

public class StudentDataModel extends UserDataModel{

    private int userId;
    private String legalFirstName;
    private String legalLastName;
    private String bio;
    private String major;
    private String minor;
    private String img;
    private boolean active;
    private Timestamp creationDate;

    public StudentDataModel(){}

    public StudentDataModel(int userId, String legalFirstName, String legalLastName, String bio, String major, String minor, String img, boolean active, Timestamp creationDate) {
        this.userId = userId;
        this.legalFirstName = legalFirstName;
        this.legalLastName = legalLastName;
        this.bio = bio;
        this.major = major;
        this.minor = minor;
        this.img = img;
        this.active = active;
        this.creationDate = creationDate;
    }

    public StudentDataModel(int userId, String legalFirstName, String legalLastName, String bio, String major, String minor, String img, boolean active, Timestamp creationDate,String userName, String email, String salt, String passhash, String userType, String[] subjects) {
        super(userId, userName, email, salt, passhash, userType, subjects);
        this.userId = userId;
        this.legalFirstName = legalFirstName;
        this.legalLastName = legalLastName;
        this.bio = bio;
        this.major = major;
        this.minor = minor;
        this.img = img;
        this.active = active;
        this.creationDate = creationDate;
    }

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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentDataModel)) return false;
        StudentDataModel student = (StudentDataModel) o;
        return userId == student.userId &&
                active == student.active &&
                Objects.equals(legalFirstName, student.legalFirstName) &&
                Objects.equals(legalLastName, student.legalLastName) &&
                Objects.equals(bio, student.bio) &&
                Objects.equals(major, student.major) &&
                Objects.equals(minor, student.minor) &&
                Objects.equals(img, student.img) &&
                Objects.equals(creationDate, student.creationDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, legalFirstName, legalLastName, bio, major, minor, img, active, creationDate);
    }

    @Override
    public String toString() {
        return "Student{" + super.toString() +
                "userId=" + userId +
                ", legalFirstName='" + legalFirstName + '\'' +
                ", legalLastName='" + legalLastName + '\'' +
                ", bio='" + bio + '\'' +
                ", major='" + major + '\'' +
                ", minor='" + minor + '\'' +
                ", img='" + img + '\'' +
                ", active=" + active +
                ", creationDate=" + creationDate +
                '}';
    }
}
