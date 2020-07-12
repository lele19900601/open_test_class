package com.example.demo.service.impl;

import com.example.demo.common.ResponseJson;
import com.example.demo.dao.UserRepository;
import com.example.demo.dao.UserRoleRepository;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserRoleEntity;
import com.example.demo.entity.dto.UserEntityDto;
import com.example.demo.service.UserService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public ResponseJson save(UserEntity userEntity) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(userEntity.getId() == null ) {
            userEntity.setUserPassport(bCryptPasswordEncoder.encode(userEntity.getUserPassport()));
            Optional<UserEntity> user = userRepository.findByUserAccount(userEntity.getUserAccount());
            if(user.isPresent() == true) {
                return ResponseJson.Error(500,"该账号已存在",null);
            }
        } else {
            Map<Integer, Object> param = new LinkedHashMap<>();
            param.put(1,userEntity.getId());
            param.put(2,userEntity.getUserAccount());
            //组装sql语句
            StringBuilder sql = new StringBuilder("select * from User_Entity ue ");
            sql.append(" where 1=1 and ue.id != ? and ue.user_account = ? ");
            Query dataQuery = entityManager.createNativeQuery(sql.toString());
            //动态设置参数
            for (Map.Entry<Integer, Object> entry : param.entrySet()) {
                dataQuery.setParameter(entry.getKey(), entry.getValue());
            }

            dataQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List<Map<String,Object>> subscribeEntityList = dataQuery.getResultList();

            if(subscribeEntityList !=null && subscribeEntityList.size() > 0) {
                return ResponseJson.Error(500,"该账号已存在",null);
            }
        }
        UserEntity result = userRepository.save(userEntity);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(result.getId());
        userRoleEntity.setRoleId(Long.valueOf(result.getType()));
        userRoleRepository.deleteByUserId(result.getId());
        userRoleRepository.save(userRoleEntity);
        return ResponseJson.Success(result);
    }

    @Override
    public ResponseJson delete(UserEntity userEntity) {
        userRepository.deleteById(userEntity.getId());
        return ResponseJson.Success();
    }

    @Override
    public UserEntity getDetail(UserEntity userEntity) {
        Optional<UserEntity> result = userRepository.findById(userEntity.getId());
        return result.get();
    }

    @Override
    public ResponseJson findByUserAccount(UserEntity userEntity) {
        ResponseJson responseJson = new ResponseJson();

        Optional<UserEntity> result = userRepository.findByUserAccount(userEntity.getUserAccount());
        if(result.isPresent()== false) {
            responseJson.setStatus(500);
            responseJson.setDesc("该账号不存在");
            return responseJson;
        }
        UserEntity user = result.get();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(userEntity.getUserPassport(),user.getUserPassport())) {
            responseJson.setStatus(500);
            responseJson.setDesc("密码错误");
            return responseJson;
        }
        responseJson.setStatus(200);
        responseJson.setDesc("登陆成功");
        responseJson.setData(result.get());
        return responseJson;
    }

    @Override
    public List<UserEntity> list() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        UserEntityDto userEntityDto  = new UserEntityDto();
        Optional<UserEntity> userEntity = userRepository.findByUserAccount(userAccount);
        if(userEntity.isPresent()) {
            Map<Integer, Object> param = new LinkedHashMap<>();
            param.put(1,userEntity.get().getId());
            List<RoleEntity> roleEntityList = userRoleRepository.findAllByUserId(userEntity.get().getId());
            BeanUtils.copyProperties(userEntity.get(),userEntityDto);
            userEntityDto.setRoles(roleEntityList);
        } else {
            throw new UsernameNotFoundException("该账号不存在");
        }
        return userEntityDto;
    }
}
