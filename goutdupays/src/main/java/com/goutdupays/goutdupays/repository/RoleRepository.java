package com.goutdupays.goutdupays.repository;

import com.goutdupays.goutdupays.modele.ERole;
import com.goutdupays.goutdupays.modele.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}