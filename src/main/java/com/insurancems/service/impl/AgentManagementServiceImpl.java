package com.insurancems.service.impl;

import com.insurancems.entity.Agent;
import com.insurancems.repository.AgentRepository;
import com.insurancems.service.AgentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AgentManagementServiceImpl implements AgentManagementService {

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public Agent createAgent(Agent agent) {
        if (existsByLicenseNumber(agent.getLicenseNumber())) {
            throw new RuntimeException("License number already exists");
        }
        return agentRepository.save(agent);
    }

    @Override
    public Agent updateAgent(Long id, Agent agent) {
        Agent existingAgent = getAgentById(id);
        if (!existingAgent.getLicenseNumber().equals(agent.getLicenseNumber()) 
                && existsByLicenseNumber(agent.getLicenseNumber())) {
            throw new RuntimeException("License number already exists");
        }
        
        existingAgent.setLicenseNumber(agent.getLicenseNumber());
        existingAgent.setSpecialization(agent.getSpecialization());
        existingAgent.setCommissionRate(agent.getCommissionRate());
        existingAgent.setUpdatedAt(LocalDateTime.now());
        
        return agentRepository.save(existingAgent);
    }

    @Override
    public void deleteAgent(Long id) {
        Agent agent = getAgentById(id);
        agent.setActive(false);
        agent.setUpdatedAt(LocalDateTime.now());
        agentRepository.save(agent);
    }

    @Override
    public Agent getAgentById(Long id) {
        return agentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agent not found"));
    }

    @Override
    public Agent getAgentByUsername(String username) {
        return agentRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Agent not found"));
    }

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public List<Agent> getActiveAgents() {
        return agentRepository.findByActiveTrue();
    }

    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        return agentRepository.existsByLicenseNumber(licenseNumber);
    }
} 