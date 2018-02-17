package com.tutorfind.model;

/*
Author: Bryan
 */

import java.util.Objects;

public class SubjectDataModel {

    // PRIMARY KEY, subjectId
    private int subjectId;
    private String subjectName;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDataModel that = (SubjectDataModel) o;
        return subjectId == that.subjectId &&
                Objects.equals(subjectName, that.subjectName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(subjectId, subjectName);
    }

    @Override
    public String toString() {
        return "subjectDataModel{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}

