package com.project.adf.model;

public class User {
    private String UserName;
    private String UserId;

    public User(String userName, String userId) {
        UserName = userName;
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
