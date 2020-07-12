package com.example.demo.entity.dto;

import lombok.Data;

@Data
public class RoleMenuEntityDto {

    private Integer id;

    private Integer roleId;

    private Integer menuId;

    private String url;

    private String name;

}
