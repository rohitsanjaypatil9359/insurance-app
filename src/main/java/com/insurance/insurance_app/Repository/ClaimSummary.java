package com.insurance.insurance_app.Repository;

public interface ClaimSummary {
    Long getId();
    String getReason();
    double getAmount();
    String getStatus();
    String getFiledDate();
}
