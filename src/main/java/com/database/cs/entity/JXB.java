package com.database.cs.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 教学班实体类，记录了一个教学班的上课地点、时间、人数上限等信息
 */
@Entity
@DynamicUpdate
@Data
public class JXB {

    @Id
    @GeneratedValue
    private Integer id;

    private String jxbId;

    private String jxbName;


}
