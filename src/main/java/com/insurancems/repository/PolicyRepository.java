package com.insurancems.repository;

import com.insurancems.entity.Policy;
import com.insurancems.entity.enums.PolicyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Optional<Policy> findByPolicyNumber(String policyNumber);
    List<Policy> findByUserUsername(String username);
    List<Policy> findByAgentUserUsername(String username);
    List<Policy> findByStatus(PolicyStatus status);
    boolean existsByPolicyNumber(String policyNumber);
} 