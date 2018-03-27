package com.tutorfind.model;

import java.sql.Timestamp;
import java.util.Objects;

public class PostDataModel {

    private int postId;
    private String posterType;
    private int ownerId;
    private int subjectId;
    private String location;
    private String availability;
    private boolean acceptsPaid;
    private double rate;
    private String unit;
    private Timestamp createdTs;
    private boolean active;
    private boolean acceptsGroupTutoring;
    private int currentlySignedUp;

    public PostDataModel() {
    }

    public PostDataModel(int postId, String posterType, int ownerId, int subjectId, String location,
                         String availability, boolean acceptsPaid, double rate, String unit, Timestamp createdTs,
                         boolean active, boolean acceptsGroupTutoring, int currentlySignedUp) {
        this.postId = postId;
        this.posterType = posterType;
        this.ownerId = ownerId;
        this.subjectId = subjectId;
        this.location = location;
        this.availability = availability;
        this.acceptsPaid = acceptsPaid;
        this.rate = rate;
        this.unit = unit;
        this.createdTs = createdTs;
        this.active = active;
        this.acceptsGroupTutoring = acceptsGroupTutoring;
        this.currentlySignedUp = currentlySignedUp;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPosterType() {
        return posterType;
    }

    public void setPosterType(String posterType) {
        this.posterType = posterType;
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public boolean isAcceptsPaid() {
        return acceptsPaid;
    }

    public void setAcceptsPaid(boolean acceptsPaid) {
        this.acceptsPaid = acceptsPaid;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Timestamp getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Timestamp createdTs) {
        this.createdTs = createdTs;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAcceptsGroupTutoring() {
        return acceptsGroupTutoring;
    }

    public void setAcceptsGroupTutoring(boolean acceptsGroupTutoring) {
        this.acceptsGroupTutoring = acceptsGroupTutoring;
    }

    public int getCurrentlySignedUp() {
        return currentlySignedUp;
    }

    public void setCurrentlySignedUp(int currentlySignedUp) {
        this.currentlySignedUp = currentlySignedUp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostDataModel)) return false;
        PostDataModel that = (PostDataModel) o;
        return postId == that.postId &&
                Objects.equals(posterType, that.posterType) &&
                ownerId == that.ownerId &&
                subjectId == that.subjectId &&
                Objects.equals(location, that.location) &&
                Objects.equals(availability, that.availability)&&
                acceptsPaid == that.acceptsPaid &&
                Double.compare(that.rate, rate) == 0 &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(createdTs, that.createdTs) &&
                active == that.active &&
                acceptsGroupTutoring == that.acceptsGroupTutoring &&
                currentlySignedUp == that.currentlySignedUp;
    }

    @Override
    public int hashCode() {

        return Objects.hash(postId, posterType, ownerId, subjectId, location, availability, acceptsPaid, rate, unit, createdTs, active, acceptsGroupTutoring, currentlySignedUp);
    }


    @Override
    public String toString() {
        return "PostDataModel{" +
                "postid=" + postId +
                ", posterType=" + posterType +
                ", ownerId=" + ownerId +
                ", subjectId=" + subjectId +
                ", location='" + location + '\'' +
                ", availability=" + availability +
                ", acceptsPaid=" + acceptsPaid +
                ", rate=" + rate +
                ", unit='" + unit + '\'' +
                ", createdTs=" + createdTs +
                ", active=" + active +
                ", acceptsGroupTutoring=" + acceptsGroupTutoring +
                ", currentlySignedUp=" + currentlySignedUp +
                "}";
    }

}