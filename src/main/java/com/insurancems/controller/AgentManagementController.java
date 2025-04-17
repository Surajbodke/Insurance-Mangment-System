package com.insurancems.controller;

import com.insurancems.entity.Agent;
import com.insurancems.service.AgentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/agents")
public class AgentManagementController {

    @Autowired
    private AgentManagementService agentManagementService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String listAgents(Model model) {
        model.addAttribute("agents", agentManagementService.getAllAgents());
        return "admin/agents";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewAgent(@PathVariable Long id, Model model) {
        model.addAttribute("agent", agentManagementService.getAgentById(id));
        return "admin/agent-details";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateForm(Model model) {
        model.addAttribute("agent", new Agent());
        return "admin/agent-form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createAgent(@ModelAttribute Agent agent) {
        agentManagementService.createAgent(agent);
        return "redirect:/agents";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("agent", agentManagementService.getAgentById(id));
        return "admin/agent-form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateAgent(@PathVariable Long id, @ModelAttribute Agent agent) {
        agentManagementService.updateAgent(id, agent);
        return "redirect:/agents";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAgent(@PathVariable Long id) {
        agentManagementService.deleteAgent(id);
        return "redirect:/agents";
    }
} 