package com.example.demo.dao;

import com.example.demo.entity.RoleMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenuEntity,Long> {

}
