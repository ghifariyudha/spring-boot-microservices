package com.example.systemservice.repository;

import com.example.systemservice.domain.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Boolean existsByUser_IdUserAndRole_IdRole(Long idUser, Long idRole);
    List<UserRole> findByUser_IdUserAndIsActiveTrue(Long idUser);
    List<UserRole> findByIsActiveTrue(Sort sort);
    Page<UserRole> findByIsActiveTrue(Pageable pgb);
}
