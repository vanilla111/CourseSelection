package com.database.cs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class JxbOperateLog {

    private Integer id;

    private String who;

    private Date createdAt;

    private String desc;

    private String ip;
}
