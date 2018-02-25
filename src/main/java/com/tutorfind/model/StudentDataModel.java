package com.tutorfind.model;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.*;


@Entity
@Table(name = "students")
public class StudentDataModel {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int userId;

    @Column(name = "legalFirstName")
    private String legalFirstName;
    @Column(name = "legalLastName")
    private String legalLastName;
    @Column(name = "bio")
    private String bio;
    @Column(name = "major")
    private String major;
    @Column(name = "minor")
    private String minor;
    @Column(name = "img")
    private String img;
    @Column(name = "active")
    private boolean active;
    @Column(name = "creationDate")
    private Timestamp creationDate;

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
        return "Student{" +
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
