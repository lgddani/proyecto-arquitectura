package diegosWafles.domain.model.entities;

public class Provider {
    private Integer providerID;
    private String providerName;
    private String providerPhone;

    public Provider() {}

    public Provider(Integer providerID, String providerName, String providerPhone) {
        this.providerID = providerID;
        this.providerName = providerName;
        this.providerPhone = providerPhone;
    }

    public Integer getProviderID() {
        return providerID;
    }

    public void setProviderID(Integer providerID) {
        this.providerID = providerID;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }
}