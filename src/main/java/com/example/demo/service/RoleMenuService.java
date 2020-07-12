package com.example.demo.service;

import com.example.demo.entity.UserRoleEntity;
import com.example.demo.entity.dto.RoleMenuEntityDto;

import java.util.List;

public interface RoleMenuService {

    List<RoleMenuEntityDto> listMenus(UserRoleEntity userRoleEntity);

}
