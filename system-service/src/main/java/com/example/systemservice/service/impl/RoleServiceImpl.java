package com.example.systemservice.service.impl;

import com.example.systemservice.domain.entity.Role;
import com.example.systemservice.repository.RoleRepository;
import com.example.systemservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository repository;

    @Override
    public Role save(Role req) {
        Role role = new Role();
        role.setRoleName(req.getRoleName());
        if (req.getIdRole()!=null) {
            role = repository.getById(req.getIdRole());
            role.setRoleName(req.getRoleName());
            role.setIsActive(req.getIsActive());
        }
        return repository.save(role);
    }

    @Override
    public Boolean delete(Long id) {
        Optional<Role> optional = repository.findById(id);
        if (optional.isPresent()) {
            Role role = optional.get();
            role.setIsActive(false);
            repository.save(role);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Role> getOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return repository.findByIsActiveTrue(Sort.by("idRole").descending());
    }

    @Override
    public Page<Role> findAll(Pageable pgb) {
        return repository.findByIsActiveTrue(pgb);
    }
}