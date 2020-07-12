package com.example.demo.service;

import com.example.demo.common.ResponseJson;
import com.example.demo.entity.SubscribeEntity;
import com.example.demo.entity.dto.SubscribeEntityDto;

import java.util.List;

public interface SubscribeService {

    ResponseJson save(SubscribeEntity subscribeEntity);

    ResponseJson delete(SubscribeEntity subscribeEntity);

    List<SubscribeEntityDto> list(SubscribeEntity subscribeEntity);
}
