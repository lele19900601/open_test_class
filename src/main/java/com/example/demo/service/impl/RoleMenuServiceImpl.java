package com.example.demo.service.impl;

import com.example.demo.entity.UserRoleEntity;
import com.example.demo.entity.dto.RoleMenuEntityDto;
import com.example.demo.service.RoleMenuService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RoleMenuEntityDto> listMenus(UserRoleEntity userRoleEntity) {

        Map<Integer, Object> param = new LinkedHashMap<>();
        param.put(1,userRoleEntity.getRoleId());
        //组装sql语句
        StringBuilder sql = new StringBuilder("select me.url,ure.role_id as roleId,me.name, me.id from Role_Menu_Entity ure " +
                " left join Menu_Entity me on me.id = ure.menu_id  ");
        sql.append("where 1=1 and ure.role_id= ? ");
        Query dataQuery = entityManager.createNativeQuery(sql.toString());
        //动态设置参数
        for (Map.Entry<Integer, Object> entry : param.entrySet()) {
            dataQuery.setParameter(entry.getKey(), entry.getValue());
        }
        dataQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<RoleMenuEntityDto> roleMenuEntityDtoList = dataQuery.getResultList();
        return roleMenuEntityDtoList;
    }
}
