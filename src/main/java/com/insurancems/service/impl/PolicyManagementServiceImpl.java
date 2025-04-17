package com.insurancems.service.impl;

import com.insurancems.entity.Policy;
import com.insurancems.entity.enums.PolicyStatus;
import com.insurancems.repository.PolicyRepository;
import com.insurancems.service.PolicyManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PolicyManagementServiceImpl implements PolicyManagementService {

    @Autowired
    private PolicyRepository policyRepository;

    @Override
    public Policy createPolicy(Policy policy) {
        if (existsByPolicyNumber(policy.getPolicyNumber())) {
            throw new RuntimeException("Policy number already exists");
        }
        return policyRepository.save(policy);
    }

    @Override
    public Policy updatePolicy(Long id, Policy policy) {
        Policy existingPolicy = getPolicyById(id);
        if (!existingPolicy.getPolicyNumber().equals(policy.getPolicyNumber()) 
                && existsByPolicyNumber(policy.getPolicyNumber())) {
            throw new RuntimeException("Policy number already exists");
        }
        
        existingPolicy.setPolicyType(policy.getPolicyType());
        existingPolicy.setPremiumAmount(policy.getPremiumAmount());
        existingPolicy.setCoverageAmount(policy.getCoverageAmount());
        existingPolicy.setStartDate(policy.getStartDate());
        existingPolicy.setEndDate(policy.getEndDate());
        existingPolicy.setStatus(policy.getStatus());
        existingPolicy.setUpdatedAt(LocalDateTime.now());
        
        return policyRepository.save(existingPolicy);
    }

    @Override
    public void deletePolicy(Long id) {
        Policy policy = getPolicyById(id);
        policy.setStatus(PolicyStatus.CANCELLED);
        policy.setUpdatedAt(LocalDateTime.now());
        policyRepository.save(policy);
    }

    @Override
    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
    }

    @Override
    public Policy getPolicyByNumber(String policyNumber) {
        return policyRepository.findByPolicyNumber(policyNumber)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
    }

    @Override
    public List<Policy> getPoliciesByUser(String username) {
        return policyRepository.findByUserUsername(username);
    }

    @Override
    public List<Policy> getPoliciesByAgent(String username) {
        return policyRepository.findByAgentUserUsername(username);
    }

    @Override
    public List<Policy> getPoliciesByStatus(PolicyStatus status) {
        return policyRepository.findByStatus(status);
    }

    @Override
    public boolean existsByPolicyNumber(String policyNumber) {
        return policyRepository.existsByPolicyNumber(policyNumber);
    }
} 