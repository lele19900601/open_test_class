package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 订阅实体类
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String subjectId;

    /**
     * 删除标志 1：正常  2：删除
     */
    private Integer isDelete;

    /**
     * 订阅状态 1：订阅  2：取消订阅
     */
    private Integer status;
}
