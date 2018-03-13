package com.tutorfind.model;

/*
Author: Bryan
 */

import java.util.Objects;

public class TutorRatingDataModel {


    // FOREIGN KEY (tutorUserId) REFERENCES tutors(userId),
    private int tutorUserId;

    // FOREIGN KEY (studentUserId) REFERENCES students(userId),
    private int studentUserId;

    //FOREIGN KEY (tutorPostId) REFERENCES tutorPost(tutorPostId)
    private int tutorPostId;

    private int rating;
    private String subject;
    private String comments;

    public TutorRatingDataModel(){}

    public TutorRatingDataModel(int tutorUserId, int studentUserId,int tutorPostId, int rating, String subject, String comments){
        this.tutorUserId = tutorUserId;
        this.studentUserId = studentUserId;
        this.tutorPostId = tutorPostId;
        this.rating = rating;
        this.subject = subject;
        this.comments = comments;
    }

    public int getTutorUserId() {
        return tutorUserId;
    }

    public void setTutorUserId(int tutorUserId) {
        this.tutorUserId = tutorUserId;
    }

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    public int getTutorPostId() {
        return tutorPostId;
    }

    public void setTutorPostId(int tutorPostId) {
        this.tutorPostId = tutorPostId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TutorRatingDataModel that = (TutorRatingDataModel) o;
        return tutorUserId == that.tutorUserId &&
                studentUserId == that.studentUserId &&
                tutorPostId == that.tutorPostId &&
                rating == that.rating &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tutorUserId, studentUserId, tutorPostId, rating, subject, comments);
    }

    @Override
    public String toString() {
        return "tutorRatingDataModel{" +
                "tutorUserId=" + tutorUserId +
                ", studentUserId=" + studentUserId +
                ", tutorPostId=" + tutorPostId +
                ", rating=" + rating +
                ", subject='" + subject + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
