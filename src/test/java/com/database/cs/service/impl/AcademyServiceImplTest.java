package com.database.cs.service.impl;

import com.database.cs.common.Constant;
import com.database.cs.dao.AcademyDao;
import com.database.cs.entity.Academy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AcademyServiceImplTest {
    @Autowired
    AcademyDao academyDao;

    @Test
    public void getAll() {
        for (Constant.QType type : Constant.QType.values()) {
            Academy academy = new Academy();
            academy.setAcadName(type.getQType());
            academyDao.save(academy);
        }
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }
}