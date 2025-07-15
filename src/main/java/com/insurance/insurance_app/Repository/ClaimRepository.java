package com.insurance.insurance_app.Repository;

import com.insurance.insurance_app.Model.Claim;
import com.insurance.insurance_app.Model.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    List<Claim> findByUserId(Long userId);

    List<Claim> findByStatus(ClaimStatus status);

    List<Claim> findByInsurancePolicyId(Long policyId);

    boolean existsByUserIdAndInsurancePolicyIdAndStatus(Long userId, Long policyId, ClaimStatus status);

    // Optional DTO-based query
    @Query("SELECT c.id AS id, c.reason AS reason, c.amount AS amount, c.status AS status, c.filedDate AS filedDate FROM Claim c WHERE c.user.id = :userId")
    List<ClaimSummary> findSummaryByUserId(@Param("userId") Long userId);
}    