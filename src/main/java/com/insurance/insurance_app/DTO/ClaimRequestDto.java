package com.insurance.insurance_app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimRequestDto {
    private Long policyId;
    private String reason;
    private double amount;
}
