package diegosWafles.domain.model.dto;

public class UserDetailDTO {
    private Integer userID;
    private String userName;
    private String userEmail;
    private String userPhone;
    private boolean userStatus;
    private Integer rolID;
    private String rolName;

    public UserDetailDTO() {}

    public UserDetailDTO(Integer userID, String userName, String userEmail, String userPhone,
                         boolean userStatus, Integer rolID, String rolName) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userStatus = userStatus;
        this.rolID = rolID;
        this.rolName = rolName;
    }

    // Getters y Setters
    public Integer getUserID() { return userID; }
    public void setUserID(Integer userID) { this.userID = userID; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }

    public boolean isUserStatus() { return userStatus; }
    public void setUserStatus(boolean userStatus) { this.userStatus = userStatus; }

    public Integer getRolID() { return rolID; }
    public void setRolID(Integer rolID) { this.rolID = rolID; }

    public String getRolName() { return rolName; }
    public void setRolName(String rolName) { this.rolName = rolName; }
}