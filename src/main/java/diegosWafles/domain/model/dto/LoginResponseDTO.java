package diegosWafles.domain.model.dto;

public class LoginResponseDTO {
    private Integer userID;
    private String token;
    private String email;
    private Integer rol;

    public LoginResponseDTO() {}

    public LoginResponseDTO(Integer userID, String token, String email, Integer rol) {
        this.userID = userID;
        this.token = token;
        this.email = email;
        this.rol = rol;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }
}