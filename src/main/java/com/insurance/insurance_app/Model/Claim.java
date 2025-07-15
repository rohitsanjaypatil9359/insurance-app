package com.insurance.insurance_app.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status; // PENDING, APPROVED, REJECTED

    private LocalDate filedDate;

    private String reason;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private InsurancePolicy insurancePolicy;

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }
    
    public ClaimStatus getStatus() {
        return status;
    }

}
