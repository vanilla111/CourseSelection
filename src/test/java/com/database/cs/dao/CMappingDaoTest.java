package com.database.cs.dao;

import com.database.cs.entity.CMapping;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CMappingDaoTest {

    @Autowired
    private CMappingDao cMappingDao;

    @Test
    public void getByCourseCode() {
        System.out.println(cMappingDao.getByCourseCode("A1090020"));
    }

    @Test
    public void findByCodeContaining() {
        PageHelper.startPage(1, 10);
        List<CMapping> page = cMappingDao.findByCodeContaining("A");
        for (CMapping cMapping : page) {
            System.out.println(cMapping);
        }
    }

    @Test
    public void save() {
        CMapping cMapping = new CMapping();
        cMapping.setCourseCode("test");
        cMapping.setCourseName("测试课程");
        System.out.println(cMappingDao.save(cMapping));
    }
}