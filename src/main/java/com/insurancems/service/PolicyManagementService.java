package com.insurancems.service;

import com.insurancems.entity.Policy;
import com.insurancems.entity.enums.PolicyStatus;
import java.util.List;

public interface PolicyManagementService {
    Policy createPolicy(Policy policy);
    Policy updatePolicy(Long id, Policy policy);
    void deletePolicy(Long id);
    Policy getPolicyById(Long id);
    Policy getPolicyByNumber(String policyNumber);
    List<Policy> getPoliciesByUser(String username);
    List<Policy> getPoliciesByAgent(String username);
    List<Policy> getPoliciesByStatus(PolicyStatus status);
    boolean existsByPolicyNumber(String policyNumber);
} 