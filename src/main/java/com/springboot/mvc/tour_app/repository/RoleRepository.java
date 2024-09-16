package com.springboot.mvc.tour_app.repository;

import com.springboot.mvc.tour_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
