package com.insurance.insurance_app.Service;

import com.insurance.insurance_app.DTO.InsurancePolicyDto;
import com.insurance.insurance_app.Model.InsurancePolicy;
import com.insurance.insurance_app.Repository.InsurancePolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

    private final InsurancePolicyRepository policyRepository;

    @Override
    public InsurancePolicyDto addPolicy(InsurancePolicyDto dto) {
        InsurancePolicy policy = InsurancePolicy.builder()
                .name(dto.getName())
                .type(dto.getType())
                .description(dto.getDescription())
                .premium(dto.getPremium())
                .duration(dto.getDuration())
                .build();

        InsurancePolicy saved = policyRepository.save(policy);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public List<InsurancePolicyDto> getAllPolicies() {
        return policyRepository.findAll().stream().map(p ->
                new InsurancePolicyDto(
                        p.getId(), p.getName(), p.getType(),
                        p.getDescription(), p.getPremium(), p.getDuration()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public InsurancePolicyDto updatePolicy(Long id, InsurancePolicyDto dto) {
        InsurancePolicy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        policy.setName(dto.getName());
        policy.setType(dto.getType());
        policy.setDescription(dto.getDescription());
        policy.setPremium(dto.getPremium());
        policy.setDuration(dto.getDuration());

        policyRepository.save(policy);
        dto.setId(id);
        return dto;
    }

    @Override
    public void deletePolicy(Long id) {
        policyRepository.deleteById(id);
    }
}