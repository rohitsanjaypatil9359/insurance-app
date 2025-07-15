package com.insurance.insurance_app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurance.insurance_app.Model.InsurancePolicy;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {
    List<InsurancePolicy> findByType(String type);
}
