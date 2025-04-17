package com.insurancems.repository;

import com.insurancems.entity.Claim;
import com.insurancems.entity.enums.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    Optional<Claim> findByClaimNumber(String claimNumber);
    List<Claim> findByUserUsername(String username);
    List<Claim> findByPolicyPolicyNumber(String policyNumber);
    List<Claim> findByStatus(ClaimStatus status);
    boolean existsByClaimNumber(String claimNumber);
} 