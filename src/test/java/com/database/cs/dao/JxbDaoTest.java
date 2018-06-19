package com.database.cs.dao;

import com.database.cs.entity.JXB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JxbDaoTest {

    @Autowired
    private JxbDao jxbDao;

    @Test
    public void saveBatch() {
    }

    @Test
    public void saveNewJxb() {
        JXB jxb = new JXB();
        jxb.setCourseCode("A10001");
        jxbDao.saveNewJxb(jxb);
    }
}