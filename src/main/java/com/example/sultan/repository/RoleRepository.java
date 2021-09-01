package com.example.sultan.repository;

import com.example.sultan.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByroleName(String roleName);
}
