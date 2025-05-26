package diegosWafles.domain.model.dto;

public class UserUpdateDTO {
    private String userName;
    private String userEmail;
    private Integer rolID;

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public Integer getRolID() { return rolID; }
    public void setRolID(Integer rolID) { this.rolID = rolID; }
}
