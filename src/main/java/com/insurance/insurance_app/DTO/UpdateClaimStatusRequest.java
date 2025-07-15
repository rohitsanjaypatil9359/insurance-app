package com.insurance.insurance_app.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UpdateClaimStatusRequest {
    @JsonProperty("id")
    private Long claimId;
    private String status;

    // Add getter for claimId
    public Long getClaimId() {
        return claimId;
    }

    // Add getter for status
    public String getStatus() {
        return status;
    }

    // Optionally, add setters if needed
    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}