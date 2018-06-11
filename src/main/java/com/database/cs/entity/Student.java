package com.database.cs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
@Table()
public class Student {

    @Id
    @GeneratedValue
    private Integer sid;

    @Column(unique = true, nullable = false, length = 50)
    private String stuId;

    @Column(length = 2)
    private String gender;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 50)
    private String academy;

    @Column(length = 50)
    private String major;

    @Column(length = 50)
    private String classNum;

    private String password;

    private String headUrl;

    @JsonIgnore
    private Date birthday;
}
