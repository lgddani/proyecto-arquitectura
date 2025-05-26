package diegosWafles.domain.model.entities;

import java.util.Objects;

public class User {
    private Integer userID;
    private String userName;
    private String userEmail;
    private String userPassword;
    private boolean userStatus;
    private Role role;

    public User() {}

    public User(Integer userID, String userName, String userEmail, String userPassword, boolean userStatus, Role role) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userStatus = userStatus;
        this.role = role;
    }

    public Integer getUserID() { return userID; }
    public void setUserID(Integer userID) { this.userID = userID; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public boolean isUserStatus() { return userStatus; }
    public void setUserStatus(boolean userStatus) { this.userStatus = userStatus; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(userID, user.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }
}