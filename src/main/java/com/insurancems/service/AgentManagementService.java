package com.insurancems.service;

import com.insurancems.entity.Agent;
import java.util.List;

public interface AgentManagementService {
    Agent createAgent(Agent agent);
    Agent updateAgent(Long id, Agent agent);
    void deleteAgent(Long id);
    Agent getAgentById(Long id);
    Agent getAgentByUsername(String username);
    List<Agent> getAllAgents();
    List<Agent> getActiveAgents();
    boolean existsByLicenseNumber(String licenseNumber);
} 