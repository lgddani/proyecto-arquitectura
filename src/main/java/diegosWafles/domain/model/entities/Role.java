package diegosWafles.domain.model.entities;

import java.util.Objects;

public class Role {
    private Integer rolID;
    private String rolName;

    public Role(Integer rolID, String rolName) {
        this.rolID = rolID;
        this.rolName = rolName;
    }

    public Integer getRolID() { return rolID; }
    public void setRolID(Integer rolID) { this.rolID = rolID; }

    public String getRolName() { return rolName; }
    public void setRolName(String rolName) { this.rolName = rolName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(rolID, role.rolID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolID);
    }
}
