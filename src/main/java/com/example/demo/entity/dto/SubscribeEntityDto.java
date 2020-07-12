package com.example.demo.entity.dto;

import lombok.Data;

/**
 * 订阅dto
 */
@Data
public class SubscribeEntityDto {

    private Long id;

    private String userId;

    private String subjectId;

    private String subjectName;

    private Integer status;
}
