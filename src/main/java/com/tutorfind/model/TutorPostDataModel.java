package com.tutorfind.model;

import java.sql.Timestamp;
import java.util.Objects;

public class TutorPostDataModel {

    private int tutorpostid;
    private int ownerId;
    private int subjectId;
    private String location;
    //private JsonObject availability;
    private double rate;
    private String unit;
    private Timestamp createdts;
    private boolean active;
    private int maxgroupsize;
    private int currentsignedup;
    private boolean acceptsgroups;

    public TutorPostDataModel(int tutorpostid, int ownerId, int subjectId, String location, double rate, String unit, Timestamp createdts, boolean active, int maxgroupsize, int currentsignedup, boolean acceptsgroups) {
        this.tutorpostid = tutorpostid;
        this.ownerId = ownerId;
        this.subjectId = subjectId;
        this.location = location;
        this.rate = rate;
        this.unit = unit;
        this.createdts = createdts;
        this.active = active;
        this.maxgroupsize = maxgroupsize;
        this.currentsignedup = currentsignedup;
        this.acceptsgroups = acceptsgroups;
    }

    public int getTutorpostid() {
        return tutorpostid;
    }

    public void setTutorpostid(int tutorpostid) {
        this.tutorpostid = tutorpostid;
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

    public int getMaxgroupsize() {
        return maxgroupsize;
    }

    public void setMaxgroupsize(int maxgroupsize) {
        this.maxgroupsize = maxgroupsize;
    }

    public int getCurrentsignedup() {
        return currentsignedup;
    }

    public void setCurrentsignedup(int currentsignedup) {
        this.currentsignedup = currentsignedup;
    }

    public boolean isAcceptsgroups() {
        return acceptsgroups;
    }

    public void setAcceptsgroups(boolean acceptsgroups) {
        this.acceptsgroups = acceptsgroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TutorPostDataModel)) return false;
        TutorPostDataModel that = (TutorPostDataModel) o;
        return tutorpostid == that.tutorpostid &&
                ownerId == that.ownerId &&
                subjectId == that.subjectId &&
                Double.compare(that.rate, rate) == 0 &&
                active == that.active &&
                maxgroupsize == that.maxgroupsize &&
                currentsignedup == that.currentsignedup &&
                acceptsgroups == that.acceptsgroups &&
                Objects.equals(location, that.location) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(createdts, that.createdts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tutorpostid, ownerId, subjectId, location, rate, unit, createdts, active, maxgroupsize, currentsignedup, acceptsgroups);
    }

    @Override
    public String toString() {
        return "TutorPostDataModel{" +
                "tutorpostid=" + tutorpostid +
                ", ownerId=" + ownerId +
                ", subjectId=" + subjectId +
                ", location='" + location + '\'' +
                ", rate=" + rate +
                ", unit='" + unit + '\'' +
                ", createdts=" + createdts +
                ", active=" + active +
                ", maxgroupsize=" + maxgroupsize +
                ", currentsignedup=" + currentsignedup +
                ", acceptsgroups=" + acceptsgroups +
                '}';
    }
}
