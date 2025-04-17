package com.insurancems.service.impl;

import com.insurancems.entity.Claim;
import com.insurancems.entity.enums.ClaimStatus;
import com.insurancems.repository.ClaimRepository;
import com.insurancems.service.ClaimProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ClaimProcessingServiceImpl implements ClaimProcessingService {

    @Autowired
    private ClaimRepository claimRepository;

    @Override
    public Claim createClaim(Claim claim) {
        if (existsByClaimNumber(claim.getClaimNumber())) {
            throw new RuntimeException("Claim number already exists");
        }
        return claimRepository.save(claim);
    }

    @Override
    public Claim updateClaim(Long id, Claim claim) {
        Claim existingClaim = getClaimById(id);
        if (!existingClaim.getClaimNumber().equals(claim.getClaimNumber()) 
                && existsByClaimNumber(claim.getClaimNumber())) {
            throw new RuntimeException("Claim number already exists");
        }
        
        existingClaim.setIncidentDate(claim.getIncidentDate());
        existingClaim.setIncidentDescription(claim.getIncidentDescription());
        existingClaim.setClaimAmount(claim.getClaimAmount());
        existingClaim.setUpdatedAt(LocalDateTime.now());
        
        return claimRepository.save(existingClaim);
    }

    @Override
    public void deleteClaim(Long id) {
        Claim claim = getClaimById(id);
        claim.setStatus(ClaimStatus.REJECTED);
        claim.setRejectionReason("Claim deleted by user");
        claim.setUpdatedAt(LocalDateTime.now());
        claimRepository.save(claim);
    }

    @Override
    public Claim getClaimById(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
    }

    @Override
    public Claim getClaimByNumber(String claimNumber) {
        return claimRepository.findByClaimNumber(claimNumber)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
    }

    @Override
    public List<Claim> getClaimsByUser(String username) {
        return claimRepository.findByUserUsername(username);
    }

    @Override
    public List<Claim> getClaimsByPolicy(String policyNumber) {
        return claimRepository.findByPolicyPolicyNumber(policyNumber);
    }

    @Override
    public List<Claim> getClaimsByStatus(ClaimStatus status) {
        return claimRepository.findByStatus(status);
    }

    @Override
    public Claim processClaim(Long id, ClaimStatus status, String rejectionReason) {
        Claim claim = getClaimById(id);
        claim.setStatus(status);
        if (status == ClaimStatus.REJECTED) {
            claim.setRejectionReason(rejectionReason);
        }
        claim.setUpdatedAt(LocalDateTime.now());
        return claimRepository.save(claim);
    }

    @Override
    public boolean existsByClaimNumber(String claimNumber) {
        return claimRepository.existsByClaimNumber(claimNumber);
    }
} 