package com.insurancems.controller;

import com.insurancems.entity.Claim;
import com.insurancems.entity.enums.ClaimStatus;
import com.insurancems.service.ClaimProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/claims")
public class ClaimProcessingController {

    @Autowired
    private ClaimProcessingService claimProcessingService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String listClaims(Model model) {
        model.addAttribute("claims", claimProcessingService.getAllClaims());
        return "claims/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String viewClaim(@PathVariable Long id, Model model) {
        model.addAttribute("claim", claimProcessingService.getClaimById(id));
        return "claims/details";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String showCreateForm(Model model) {
        model.addAttribute("claim", new Claim());
        return "claims/form";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String createClaim(@ModelAttribute Claim claim) {
        claimProcessingService.createClaim(claim);
        return "redirect:/claims";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("claim", claimProcessingService.getClaimById(id));
        return "claims/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String updateClaim(@PathVariable Long id, @ModelAttribute Claim claim) {
        claimProcessingService.updateClaim(id, claim);
        return "redirect:/claims";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String deleteClaim(@PathVariable Long id) {
        claimProcessingService.deleteClaim(id);
        return "redirect:/claims";
    }

    @GetMapping("/customer/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String listCustomerClaims(@PathVariable String username, Model model) {
        model.addAttribute("claims", claimProcessingService.getClaimsByUser(username));
        return "claims/customer-list";
    }

    @GetMapping("/policy/{policyNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String listPolicyClaims(@PathVariable String policyNumber, Model model) {
        model.addAttribute("claims", claimProcessingService.getClaimsByPolicy(policyNumber));
        return "claims/policy-list";
    }

    @PostMapping("/{id}/process")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public String processClaim(
            @PathVariable Long id,
            @RequestParam ClaimStatus status,
            @RequestParam(required = false) String rejectionReason) {
        claimProcessingService.processClaim(id, status, rejectionReason);
        return "redirect:/claims/" + id;
    }
} 