package diegosWafles.domain.model.dto;

public class UserListDTO {
    private String userName;
    private String userEmail;
    private String roleName;

    public UserListDTO(String userName, String userEmail, String roleName) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.roleName = roleName;
    }

    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public String getRoleName() { return roleName; }
}
