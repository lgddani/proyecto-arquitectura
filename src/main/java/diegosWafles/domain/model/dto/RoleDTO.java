package diegosWafles.domain.model.dto;

public class RoleDTO {
    private Integer rolID;
    private String rolName;

    public RoleDTO() {}

    public RoleDTO(Integer rolID, String rolName) {
        this.rolID = rolID;
        this.rolName = rolName;
    }

    public Integer getRolID() {
        return rolID;
    }

    public void setRolID(Integer rolID) {
        this.rolID = rolID;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }
}