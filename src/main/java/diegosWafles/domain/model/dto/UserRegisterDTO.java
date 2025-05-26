package diegosWafles.domain.model.dto;

public class UserRegisterDTO {
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userConfirmPassword;
    private Integer rolID;

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public String getUserConfirmPassword() { return userConfirmPassword; }
    public void setUserConfirmPassword(String userConfirmPassword) { this.userConfirmPassword = userConfirmPassword; }

    public Integer getRolID() { return rolID; }
    public void setRolID(Integer rolID) { this.rolID = rolID; }
}