package com.example.demo.dao;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Long> {

    Optional<UserRoleEntity> findUserRoleEntityByUserId(Long userId);

    @Query(value = " select new com.example.demo.entity.RoleEntity(re.id,re.roleCode,re.roleName)  from UserRoleEntity ure left join RoleEntity re on re.id = ure.roleId where 1=1 and ure.userId=?1")
    List<RoleEntity> findAllByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = " delete from UserRoleEntity ure where ure.userId=?1")
    void deleteByUserId(Long userId);
}
