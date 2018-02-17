package com.tutorfind;

/*
Author: Karl Fernando
 */

import java.sql.Timestamp;
import java.util.Objects;

public class StudentPostDataModel {

    private int studentPostId;
    private int ownerId;
    private int subjectId;
    private String location;
    //private JSONObject availability;
    private boolean acceptspaid;
    private double rate;
    private String unit;
    private Timestamp createdts;
    private boolean active;
    private boolean acceptsgrouptutoring;

    public StudentPostDataModel(int studentPostId, int ownerId, int subjectId, String location, boolean acceptspaid, double rate, String unit, Timestamp createdts, boolean active, boolean acceptsgrouptutoring) {
        this.studentPostId = studentPostId;
        this.ownerId = ownerId;
        this.subjectId = subjectId;
        this.location = location;
        this.acceptspaid = acceptspaid;
        this.rate = rate;
        this.unit = unit;
        this.createdts = createdts;
        this.active = active;
        this.acceptsgrouptutoring = acceptsgrouptutoring;
    }

    public int getStudentPostId() {
        return studentPostId;
    }

    public void setStudentPostId(int studentPostId) {
        this.studentPostId = studentPostId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAcceptspaid() {
        return acceptspaid;
    }

    public void setAcceptspaid(boolean acceptspaid) {
        this.acceptspaid = acceptspaid;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        if(rate > 0.0){
            this.rate = rate;
        }
        else {
            rate = 0.0;
        }
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Timestamp getCreatedts() {
        return createdts;
    }

    public void setCreatedts(Timestamp createdts) {
        this.createdts = createdts;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAcceptsgrouptutoring() {
        return acceptsgrouptutoring;
    }

    public void setAcceptsgrouptutoring(boolean acceptsgrouptutoring) {
        this.acceptsgrouptutoring = acceptsgrouptutoring;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentPostDataModel that = (StudentPostDataModel) o;
        return studentPostId == that.studentPostId &&
                ownerId == that.ownerId &&
                subjectId == that.subjectId &&
                Objects.equals(location, that.location) &&
                Objects.equals(acceptspaid, that.acceptspaid) &&
                rate == that.rate &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(createdts, this.createdts) &&
                Objects.equals(active, that.active) &&
                Objects.equals(acceptsgrouptutoring, that.acceptsgrouptutoring);
    }

    @Override
    public String toString() {
        return "StudentPostDataModel{" +
                "studentPostId=" + studentPostId +
                ", ownerId=" + ownerId +
                ", subjectId=" + subjectId +
                ", location='" + location + '\'' +
                ", acceptspaid=" + acceptspaid +
                ", rate=" + rate +
                ", unit='" + unit + '\'' +
                ", createdts=" + createdts +
                ", active=" + active +
                ", acceptsgrouptutoring=" + acceptsgrouptutoring +
                '}';
    }
}
