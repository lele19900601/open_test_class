package com.example.demo.service;

import com.example.demo.common.ResponseJson;
import com.example.demo.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    ResponseJson save(UserEntity userEntity);

    ResponseJson delete(UserEntity userEntity);

    UserEntity getDetail(UserEntity userEntity);

    List<UserEntity> list();

    ResponseJson findByUserAccount(UserEntity userEntity);
}
