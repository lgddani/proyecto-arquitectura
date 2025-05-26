package diegosWafles.infraestructure.output.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dw_rol")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Integer rolID;

    @Column(name = "rol_nombre", nullable = false, unique = true)
    private String rolName;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<UserEntity> users = new HashSet<>();

    public RoleEntity() {}

    public RoleEntity(Integer rolID, String rolName) {
        this.rolID = rolID;
        this.rolName = rolName;
    }

    public Integer getRolID() { return rolID; }
    public void setRolID(Integer rolID) { this.rolID = rolID; }

    public String getRolName() { return rolName; }
    public void setRolName(String rolName) { this.rolName = rolName; }

    public Set<UserEntity> getUsers() { return users; }
    public void setUsers(Set<UserEntity> users) { this.users = users; }
}