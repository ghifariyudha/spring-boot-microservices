package com.example.systemservice.service.impl;

import com.example.systemservice.domain.entity.UserRole;
import com.example.systemservice.payload.response.UserRoleResp;
import com.example.systemservice.repository.UserRoleRepository;
import com.example.systemservice.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleRepository repository;

    @Override
    public UserRole save(UserRole req) {
        UserRole userRole = new UserRole();
        userRole.setUser(req.getUser());
        userRole.setRole(req.getRole());
        if (req.getIdUserRole()!=null) {
            userRole = repository.getById(req.getIdUserRole());
            userRole.setUser(req.getUser());
            userRole.setRole(req.getRole());
            userRole.setIsActive(req.getIsActive());
        }
        return repository.save(userRole);
    }

    @Override
    public Boolean delete(Long id) {
        Optional<UserRole> optional = repository.findById(id);
        if (optional.isPresent()) {
            UserRole userRole = optional.get();
            userRole.setIsActive(false);
            repository.save(userRole);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserRoleResp getOne(Long id) {
        Optional<UserRole> opUserRole = repository.findById(id);
        if (!opUserRole.isPresent()) {
            throw new RuntimeException("Data is not found");
        }
        UserRole userRole = opUserRole.get();
        return toUserRoleResp(userRole);
    }

    @Override
    public List<UserRoleResp> findAll() {
        List<UserRoleResp> userRoleRespList = new ArrayList<>();
        List<UserRole> userRoleList = repository.findByIsActiveTrue(Sort.by("idUserRole").descending());
        for (UserRole userRole : userRoleList) {
            userRoleRespList.add(toUserRoleResp(userRole));
        }
        return userRoleRespList;
    }

    @Override
    public Page<UserRoleResp> findAll(Pageable pgb) {
        Page<UserRole> userRolePage = repository.findByIsActiveTrue(pgb);
        return userRolePage.map(u -> toUserRoleResp(u));
    }

    private UserRoleResp toUserRoleResp(UserRole userRole) {
        UserRoleResp userRoleResp = new UserRoleResp();
        userRoleResp.setIdUserRole(userRole.getIdUserRole());
        userRoleResp.setIdUser(userRole.getUser().getIdUser());
        userRoleResp.setUsername(userRole.getUser().getUsername());
        userRoleResp.setIdRole(userRole.getRole().getIdRole());
        userRoleResp.setRoleName(userRole.getRole().getRoleName());
        return userRoleResp;
    }
}
