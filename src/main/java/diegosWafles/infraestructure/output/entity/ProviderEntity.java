package diegosWafles.infraestructure.output.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dw_proveedor")
public class ProviderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proveedor_id")
    private Integer providerID;

    @Column(name = "proveedor_nombre", nullable = false)
    private String providerName;

    @Column(name = "proveedor_telefono")
    private String providerPhone;

    // –– GETTERS ––
    public Integer getProviderID() {
        return providerID;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    // –– SETTERS ––
    public void setProviderID(Integer providerID) {
        this.providerID = providerID;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }
}
