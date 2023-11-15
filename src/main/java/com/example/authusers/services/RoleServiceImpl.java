package com.example.authusers.services;

import com.example.authusers.models.Role;
import com.example.authusers.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }
}
