package com.database.cs.service.impl;

import com.database.cs.common.ServerResponse;
import com.database.cs.entity.Teacher;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ATeacherServiceImplTest {

    @Autowired
    private ATeacherServiceImpl atService;

    @Test
    public void getTeacherList() {
        ServerResponse response = atService.getTeacherList(1, 50, "2");
        //System.out.println(response.getData());
        PageInfo<Teacher> pageInfo = (PageInfo) response.getData();
        for (Teacher teacher : pageInfo.getList()) {
            System.out.println(teacher);
        }
    }

    @Test
    public void addNewTeacher() {
        Teacher teacher = new Teacher();
        teacher.setAid("0203");
        teacher.setPassword("123456");
        teacher.setName("岳西");
        teacher.setPosition("计算机辅导员");
        teacher.setTeaId("310417");
        System.out.println(atService.addNewTeacher(teacher).getMsg());
    }

    @Test
    public void updateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setAid("9999");
        teacher.setPassword("123456");
        teacher.setName("粤西");
        teacher.setPosition("计算机辅导员");
        teacher.setTeaId("012345");
        System.out.println(atService.updateTeacher(teacher));
    }

    @Test
    public void delTeacher() {
        System.out.println(atService.delTeacher("012345").getMsg());
    }
}