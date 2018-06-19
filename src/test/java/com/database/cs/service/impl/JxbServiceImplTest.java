package com.database.cs.service.impl;

import com.database.cs.common.Constant;
import com.database.cs.common.ServerResponse;
import com.database.cs.dao.CMappingDao;
import com.database.cs.entity.CMapping;
import com.database.cs.entity.JXB;
import com.database.cs.vo.TeacherCourseVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JxbServiceImplTest {

    @Autowired
    private JxbServiceImpl jxbService;

    @Autowired
    private CMappingDao cmDao;

    @Test
    public void geJxbList() {
        List<CMapping> list = cmDao.getAll();
        Map<String, String> map = new HashMap<>();
        for (CMapping mapping : list) {
            map.put(mapping.getCourseCode(), mapping.getCourseName());
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            CMapping cm = new CMapping();
            cm.setCourseCode(entry.getKey().trim());
            cm.setCourseName(entry.getValue().trim());
            cmDao.save(cm);
        }
    }

    @Test
    public void saveNewJxb() {
        JXB jxb = new JXB();
        jxb.setHashDay(0);
        jxb.setHashLesson(2);
        jxb.setPeriod(3);
        jxb.setCourseCode("Mooc57");
        jxb.setClassRoom("2102");
        jxb.setNumLimit(50);
        jxb.setTeacherId("012345");
        jxb.setWeek("1,2,3,4,5,6,8,9,10,11,12,13,14,15,16");
        jxb.setType("选修");
        jxb.setYear(Constant.YEAR);
        System.out.println(jxbService.saveNewJxb(jxb).getMsg());
    }

    @Test
    public void getTeacherCourse() {
        ServerResponse response = jxbService.getTeacherCourse("020402", 18, -1);
        List<TeacherCourseVo> list = (List<TeacherCourseVo>) response.getData();
        for (TeacherCourseVo courseVo : list) {
            System.out.println(courseVo);
        }
    }
}