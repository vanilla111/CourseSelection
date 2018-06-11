package com.database.cs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "t_academy")
public class TAcademy {
    @Id
    private String aid;

    private String acadName;
}
