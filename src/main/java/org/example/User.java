package org.example;

public class User {
    private String userID;
    private String email;
    private String username;
    private String password;

    public User(String userID, String email, String username, String password) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters and setters for the fields
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Override toString method for easy printing
    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
