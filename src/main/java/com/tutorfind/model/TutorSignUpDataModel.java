package com.tutorfind.model;

/*
Author: Bryan
 */

import java.util.Objects;

public class TutorSignUpDataModel {

    // FOREIGN KEY, tutorPostId references tutorPost(tutorPostId)
    private int tutorPostId;
    // FOREIGN KEY, studentUserId references students(userId)
    private int studentUserId;


    public TutorSignUpDataModel(int tutorPostId, int studentUserId) {
        this.tutorPostId = tutorPostId;
        this.studentUserId = studentUserId;
    }

    public int getTutorPostId() {
        return tutorPostId;
    }

    public void setTutorPostId(int tutorPostId) {
        this.tutorPostId = tutorPostId;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TutorSignUpDataModel that = (TutorSignUpDataModel) o;
        return tutorPostId == that.tutorPostId &&
                studentUserId == that.studentUserId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(tutorPostId, studentUserId);
    }

    @Override
    public String toString() {
        return "tutorSignUpDataModel{" +
                "tutorPostId=" + tutorPostId +
                ", studentUserId=" + studentUserId +
                '}';
    }
}

