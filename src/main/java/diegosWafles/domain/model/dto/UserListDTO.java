package diegosWafles.domain.model.dto;

public class UserListDTO {
    private String userName;
    private String userEmail;
    private String userPhone;
    private String roleName;

    public UserListDTO(String userName, String userEmail, String userPhone, String roleName) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.roleName = roleName;
    }

    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public String getUserPhone() { return userPhone; }
    public String getRoleName() { return roleName; }
}