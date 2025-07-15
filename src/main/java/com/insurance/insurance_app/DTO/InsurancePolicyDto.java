package com.insurance.insurance_app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsurancePolicyDto {
    private Long id;
    private String name;
    private String type;
    private String description;
    private double premium;
    private int duration; // in years
}
