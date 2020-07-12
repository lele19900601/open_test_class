package com.example.demo.dao;

import com.example.demo.entity.SubscribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity,Long> {

    @Query(value = "select new map (" +
            " se.id as subjectId, se.subjectName as subjectName, sb.status,sb.userId" +
            ") " +
            "from SubjectEntity se left join SubscribeEntity sb on sb.subjectId = se.id and sb.userId=?1 " +
            "where se.isDelete=0 ")
    List<Map<String,Object>> findSubscribeEntityByUserId(Long id);

    void findAllSubjectSubscribeByUserId(String userId);
}
