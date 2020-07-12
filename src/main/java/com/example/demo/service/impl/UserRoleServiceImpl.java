package com.example.demo.service.impl;

import com.example.demo.dao.UserRoleRepository;
import com.example.demo.entity.UserRoleEntity;
import com.example.demo.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRoleEntity getRoleIdByUserId(Long userId) {
        Optional<UserRoleEntity> userRoleEntity =  userRoleRepository.findUserRoleEntityByUserId(userId);
        return userRoleEntity.get();
    }
}
