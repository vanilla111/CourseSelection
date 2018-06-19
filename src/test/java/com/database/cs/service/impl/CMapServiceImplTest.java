package com.database.cs.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CMapServiceImplTest {

    @Autowired
    CMapServiceImpl cMapService;

    @Test
    public void getByCourseName() {
        for (Object o : cMapService.findCMappingByStrContaining(1, 1000, "4").getData().getList()) {
            System.out.println(o);
        }
    }
}