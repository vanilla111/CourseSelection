package com.database.cs.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CMappingDaoTest {

    @Autowired
    private CMappingDao cMappingDao;

    @Test
    public void getByCourseCode() {
        System.out.println(cMappingDao.getByCourseCode("A1090020"));
    }
}