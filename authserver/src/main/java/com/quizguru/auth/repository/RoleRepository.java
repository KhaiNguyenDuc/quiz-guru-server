package com.quizguru.auth.repository;

import com.quizguru.auth.model.Role;
import com.quizguru.auth.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(RoleName roleName);
}
