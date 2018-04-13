package com.example.tarasvolianskyi.firebaseadapter;

public class UsersPojo {

    String userId;
    String userName;
    String userCategory;

    public UsersPojo() {
    }

    public UsersPojo(String userId, String userName, String userCategory) {
        this.userId = userId;
        this.userName = userName;
        this.userCategory = userCategory;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }
}
