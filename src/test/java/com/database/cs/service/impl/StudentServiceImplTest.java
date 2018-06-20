package com.database.cs.service.impl;

import com.database.cs.common.ServerResponse;
import com.database.cs.vo.JxbVo;
import com.database.cs.vo.ReadyCourses;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceImplTest {

    @Autowired
    private StudentServiceImpl stuService;

    @Test
    public void courseSelect() {
        String jxbId = "SK16172020488001";
        String courseCode = "Mooc57";
        ServerResponse response = stuService.courseSelect("2015211516", jxbId);
        System.out.println(response.getMsg());
    }

    @Test
    public void getCourses() {
        ServerResponse response = stuService.getCourseList();
        List<ReadyCourses> list = (List<ReadyCourses>) response.getData();
        for (ReadyCourses courses : list) {
            System.out.println(courses.getCourseName());
            for (JxbVo jxbVo : courses.getJxbVos()) {
                System.out.println("    " + jxbVo.getJxbId());
            }
        }
    }
}