package com.example.demo.service;

import com.example.demo.common.ResponseJson;
import com.example.demo.entity.SubjectEntity;

import java.util.List;

public interface SubjectService {

    ResponseJson save(SubjectEntity subject);

    ResponseJson delete(SubjectEntity subject);

    SubjectEntity getDetail(SubjectEntity subject);

    List<SubjectEntity> list();
}
