package com.insurance.insurance_app.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type; // health, life, vehicle, etc.
    private String description;
    private double premium;
    private int duration; // in years
}
