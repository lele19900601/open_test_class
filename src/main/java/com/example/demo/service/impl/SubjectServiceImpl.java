package com.example.demo.service.impl;

import com.example.demo.common.ResponseJson;
import com.example.demo.dao.SubjectRepository;
import com.example.demo.entity.SubjectEntity;
import com.example.demo.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public ResponseJson save(SubjectEntity subject) {
        subject.setIsDelete(1);
        SubjectEntity result = subjectRepository.save(subject);
        return ResponseJson.Success(result);
    }

    @Override
    public ResponseJson delete(SubjectEntity subject) {
        subjectRepository.deleteById(subject.getId());
        return ResponseJson.Success();
    }

    @Override
    public SubjectEntity getDetail(SubjectEntity subject) {
        Optional<SubjectEntity> result = subjectRepository.findById(subject.getId());
        return result.get();
    }

    @Override
    public List<SubjectEntity> list() {
        return subjectRepository.findAll();
    }
}
