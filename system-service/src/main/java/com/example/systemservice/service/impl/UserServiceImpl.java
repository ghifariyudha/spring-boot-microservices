package com.example.systemservice.service.impl;

import com.example.systemservice.domain.entity.User;
import com.example.systemservice.repository.UserRepository;
import com.example.systemservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public Boolean delete(Long id) {
        Optional<User> optional = repository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setIsActive(false);
            repository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<User> getOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findByIsActiveTrue(Sort.by("idUser").descending());
    }

    @Override
    public Page<User> findAll(Pageable pgb) {
        return repository.findByIsActiveTrue(pgb);
    }
}
