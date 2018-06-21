package com.database.cs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class JxbOperateLog {

    private Integer id;

    private String adminId;

    private Date createdAt;

    private String text;

    private String ip;
}
