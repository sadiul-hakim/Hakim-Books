package org.learn.book_management_system.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(String role);
}
