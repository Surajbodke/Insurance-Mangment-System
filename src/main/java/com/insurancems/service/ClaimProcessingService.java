package com.insurancems.service;

import com.insurancems.entity.Claim;
import com.insurancems.entity.enums.ClaimStatus;
import java.util.List;

public interface ClaimProcessingService {
    Claim createClaim(Claim claim);
    Claim updateClaim(Long id, Claim claim);
    void deleteClaim(Long id);
    Claim getClaimById(Long id);
    Claim getClaimByNumber(String claimNumber);
    List<Claim> getClaimsByUser(String username);
    List<Claim> getClaimsByPolicy(String policyNumber);
    List<Claim> getClaimsByStatus(ClaimStatus status);
    Claim processClaim(Long id, ClaimStatus status, String rejectionReason);
    boolean existsByClaimNumber(String claimNumber);
} 