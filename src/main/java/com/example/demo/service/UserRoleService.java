package com.example.demo.service;

import com.example.demo.entity.UserRoleEntity;

public interface UserRoleService {

    UserRoleEntity getRoleIdByUserId(Long userId);
}
