package com.insurance.insurance_app.controller;

import com.insurance.insurance_app.DTO.InsurancePolicyDto;
import com.insurance.insurance_app.DTO.UpdateClaimStatusRequest;
import com.insurance.insurance_app.Model.Claim;
import com.insurance.insurance_app.Model.ClaimStatus;
import com.insurance.insurance_app.Repository.ClaimRepository;
import com.insurance.insurance_app.Service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;
    private final ClaimRepository claimRepository;

    @PostMapping("/admin/policies")
    public ResponseEntity<InsurancePolicyDto> addPolicy(@RequestBody InsurancePolicyDto dto) {
        return ResponseEntity.ok(policyService.addPolicy(dto));
    }

    @PutMapping("/admin/policies/{id}")
    public ResponseEntity<InsurancePolicyDto> updatePolicy(@PathVariable Long id, @RequestBody InsurancePolicyDto dto) {
        return ResponseEntity.ok(policyService.updatePolicy(id, dto));
    }

    @DeleteMapping("/admin/policies/{id}")
    public ResponseEntity<?> deletePolicy(@PathVariable Long id) {
        policyService.deletePolicy(id);
        return ResponseEntity.ok("Policy deleted successfully");
    }

    @GetMapping("/policies")
    public ResponseEntity<List<InsurancePolicyDto>> getAllPolicies() {
        return ResponseEntity.ok(policyService.getAllPolicies());
    }

    @PreAuthorize("hasRole('ADMIN')")
@PutMapping("/admin/claims/status")
public ResponseEntity<String> updateClaimStatus(@RequestBody UpdateClaimStatusRequest request) {
    System.out.println("Received claim ID: " + request.getClaimId());
    System.out.println("Received status: " + request.getStatus());

    if (request.getClaimId() == null) {
        return ResponseEntity.badRequest().body("Claim ID must not be null.");
    }

    return claimRepository.findById(request.getClaimId())
        .map(claim -> {
            try {
                ClaimStatus status = ClaimStatus.valueOf(request.getStatus().toUpperCase());
                claim.setStatus(status);
                claimRepository.save(claim);
                return ResponseEntity.ok("Claim status updated to " + status);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid status value: " + request.getStatus());
            }
        })
        .orElse(ResponseEntity.notFound().build());
}

// ✅ Get all claims - ADMIN
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin/claims")
public ResponseEntity<List<Claim>> getAllClaims() {
    return ResponseEntity.ok(claimRepository.findAll());
}

// ✅ Get claim by ID
@GetMapping("/claims/{id}")
public ResponseEntity<Claim> getClaimById(@PathVariable Long id) {
    return claimRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

// ✅ File new claim - User
@PostMapping("/claims")
public ResponseEntity<String> fileNewClaim(@RequestBody Claim claim) {
    claim.setStatus(ClaimStatus.PENDING);
    claim.setFiledDate(LocalDate.now());
    Claim saved = claimRepository.save(claim);
    return ResponseEntity.ok("Claim filed with ID: " + saved.getId());
}

// ✅ Update existing claim - User
@PutMapping("/claims/{id}")
public ResponseEntity<String> updateClaim(@PathVariable Long id, @RequestBody Claim updatedClaim) {
    return claimRepository.findById(id)
            .map(claim -> {
                claim.setReason(updatedClaim.getReason());
                claim.setAmount(updatedClaim.getAmount());
                claimRepository.save(claim);
                return ResponseEntity.ok("Claim updated.");
            })
            .orElse(ResponseEntity.notFound().build());
}

// ✅ Get claims by user ID
@GetMapping("/claims/user/{userId}")
public ResponseEntity<List<Claim>> getClaimsByUserId(@PathVariable Long userId) {
    return ResponseEntity.ok(claimRepository.findByUserId(userId));
}

// ✅ Get claims by status (e.g., PENDING)
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin/claims/status/{status}")
public ResponseEntity<List<Claim>> getClaimsByStatus(@PathVariable String status) {
    try {
        ClaimStatus claimStatus = ClaimStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(claimRepository.findByStatus(claimStatus));
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(null);
    }
}

// ✅ Get claims by policy ID
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin/claims/policy/{policyId}")
public ResponseEntity<List<Claim>> getClaimsByPolicyId(@PathVariable Long policyId) {
    return ResponseEntity.ok(claimRepository.findByInsurancePolicyId(policyId));
}

// ✅ Delete a claim
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/admin/claims/{id}")
public ResponseEntity<String> deleteClaim(@PathVariable Long id) {
    if (!claimRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }
    claimRepository.deleteById(id);
    return ResponseEntity.ok("Claim deleted with ID: " + id);
}


}