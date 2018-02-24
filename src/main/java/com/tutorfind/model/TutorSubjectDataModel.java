package com.tutorfind.model;

import java.util.Objects;

public class TutorSubjectDataModel {

    private int tutorUserId;
    private int subjectId;

    public TutorSubjectDataModel(int tutorUserId, int subjectId) {
        this.tutorUserId = tutorUserId;
        this.subjectId = subjectId;
    }

    public int getTutorUserId() {
        return tutorUserId;
    }

    public void setTutorUserId(int tutorUserId) {
        this.tutorUserId = tutorUserId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TutorSubjectDataModel)) return false;
        TutorSubjectDataModel that = (TutorSubjectDataModel) o;
        return tutorUserId == that.tutorUserId &&
                subjectId == that.subjectId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(tutorUserId, subjectId);
    }

    @Override
    public String toString() {
        return "TutorSubjectDataModel{" +
                "tutorUserId=" + tutorUserId +
                ", subjectId=" + subjectId +
                '}';
    }
}
