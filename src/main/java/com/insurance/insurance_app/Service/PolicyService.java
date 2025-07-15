package com.insurance.insurance_app.Service;

import com.insurance.insurance_app.DTO.InsurancePolicyDto;
import java.util.List;

public interface PolicyService {
    InsurancePolicyDto addPolicy(InsurancePolicyDto dto);
    List<InsurancePolicyDto> getAllPolicies();
    InsurancePolicyDto updatePolicy(Long id, InsurancePolicyDto dto);
    void deletePolicy(Long id);
}