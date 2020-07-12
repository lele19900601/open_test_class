package com.example.demo.service.impl;

import com.example.demo.common.ResponseJson;
import com.example.demo.dao.SubscribeRepository;
import com.example.demo.entity.SubscribeEntity;
import com.example.demo.entity.dto.SubscribeEntityDto;
import com.example.demo.service.SubscribeService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscribeServiceImpl implements SubscribeService {

    @Autowired
    private SubscribeRepository subscribeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public ResponseJson save(SubscribeEntity subscribeEntity) {
        subscribeEntity.setStatus(1);
        subscribeEntity.setIsDelete(0);
        SubscribeEntity result = subscribeRepository.save(subscribeEntity);
        return ResponseJson.Success(result);
    }

    @Override
    @Transactional
    public ResponseJson delete(SubscribeEntity subscribeEntity) {
        subscribeRepository.deleteById(subscribeEntity.getId());
        return ResponseJson.Success();
    }

    @Override
    public List<SubscribeEntityDto> list(SubscribeEntity subscribeEntity) {

        Map<Integer, Object> param = new LinkedHashMap<>();
        param.put(1,subscribeEntity.getUserId());
        //组装sql语句
        StringBuilder sql = new StringBuilder("select se.id as subjectId,sb.id as id, se.subject_name as subjectName, sb.status,sb.user_id as userId from Subject_Entity se " +
                " left join Subscribe_Entity sb on sb.subject_id = se.id  ");
        sql.append(" and sb.user_id= ? ");
        sql.append(" where se.is_delete=1 ");
        Query dataQuery = entityManager.createNativeQuery(sql.toString());
        //动态设置参数
        for (Map.Entry<Integer, Object> entry : param.entrySet()) {
            dataQuery.setParameter(entry.getKey(), entry.getValue());
        }

        dataQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<SubscribeEntityDto> subscribeEntityList = dataQuery.getResultList();

        return subscribeEntityList;
    }
}
