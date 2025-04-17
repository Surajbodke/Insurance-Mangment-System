package com.insurancems.repository;

import com.insurancems.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByUserUsername(String username);
    List<Agent> findByActiveTrue();
    boolean existsByLicenseNumber(String licenseNumber);
} 