package com.insurancems.controller;

import com.insurancems.entity.Policy;
import com.insurancems.service.PolicyManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/policies")
public class PolicyManagementController {

    @Autowired
    private PolicyManagementService policyManagementService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String listPolicies(Model model) {
        model.addAttribute("policies", policyManagementService.getAllPolicies());
        return "policies/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String viewPolicy(@PathVariable Long id, Model model) {
        model.addAttribute("policy", policyManagementService.getPolicyById(id));
        return "policies/details";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public String showCreateForm(Model model) {
        model.addAttribute("policy", new Policy());
        return "policies/form";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public String createPolicy(@ModelAttribute Policy policy) {
        policyManagementService.createPolicy(policy);
        return "redirect:/policies";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("policy", policyManagementService.getPolicyById(id));
        return "policies/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public String updatePolicy(@PathVariable Long id, @ModelAttribute Policy policy) {
        policyManagementService.updatePolicy(id, policy);
        return "redirect:/policies";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public String deletePolicy(@PathVariable Long id) {
        policyManagementService.deletePolicy(id);
        return "redirect:/policies";
    }

    @GetMapping("/customer/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'CUSTOMER')")
    public String listCustomerPolicies(@PathVariable String username, Model model) {
        model.addAttribute("policies", policyManagementService.getPoliciesByUser(username));
        return "policies/customer-list";
    }

    @GetMapping("/agent/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public String listAgentPolicies(@PathVariable String username, Model model) {
        model.addAttribute("policies", policyManagementService.getPoliciesByAgent(username));
        return "policies/agent-list";
    }
} 