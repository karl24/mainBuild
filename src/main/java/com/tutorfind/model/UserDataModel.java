package com.tutorfind.model;

import java.util.Objects;

public abstract class UserDataModel {

    private int userId;
    private String userName;
    private String email;
    private String salt;
    private String passhash;
    private String userType;

    public UserDataModel(int userId, String userName, String email, String salt, String passhash, String userType) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.salt = salt;
        this.passhash = passhash;
        this.userType = userType;
    }
    
    public UserDataModel() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDataModel)) return false;
        UserDataModel that = (UserDataModel) o;
        return userId == that.userId &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(salt, that.salt) &&
                Objects.equals(passhash, that.passhash) &&
                Objects.equals(userType, that.userType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, userName, email, salt, passhash, userType);
    }

    @Override
    public String toString() {

        return
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", salt='" + salt + '\'' +
                ", passhash='" + passhash + '\'' +
                ", userType='" + userType + '\'';
    }
}
