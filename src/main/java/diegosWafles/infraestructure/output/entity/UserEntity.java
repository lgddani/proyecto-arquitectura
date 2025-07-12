package diegosWafles.infraestructure.output.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dw_usuario")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer userID;

    @Column(name = "usuario_nombre", nullable = false)
    private String userName;

    @Column(name = "usuario_correo", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "usuario_telefono")
    private String userPhone;

    @Column(name = "usuario_password", nullable = false)
    private String userPassword;

    @Column(name = "usuario_estado", nullable = false)
    private boolean userStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id", nullable = false)
    private RoleEntity role;

    public UserEntity() {}

    public UserEntity(Integer userID, String userName, String userEmail, String userPhone, String userPassword, boolean userStatus, RoleEntity role) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
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

    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public boolean isUserStatus() { return userStatus; }
    public void setUserStatus(boolean userStatus) { this.userStatus = userStatus; }

    public RoleEntity getRole() { return role; }
    public void setRole(RoleEntity role) { this.role = role; }
}