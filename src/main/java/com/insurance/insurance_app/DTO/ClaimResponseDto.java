package com.insurance.insurance_app.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimResponseDto {
    private Long claimId;
    private String status;
    private LocalDate filedDate;
    private String policyName;
    private String reason;
    private double amount;
}
