package com.database.cs.controller;

import com.database.cs.common.ServerResponse;
import com.database.cs.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentServiceImpl stuService;

    @PostMapping(value = "/courses")
    public ServerResponse getReadyCourses() {
        return stuService.getCourseList();
    }

    @GetMapping(value = "/courses/{stuId}")
    public ServerResponse getStuCourses(@PathVariable(value = "stuId") String stuId,
                                        int week) {
        return stuService.getStudentCourses(stuId, week);
    }

    @GetMapping(value = "/cs.do")
    public ServerResponse courseSelect(@RequestParam(value = "token") String token, String jxbId) {
        return stuService.courseSelect(token, jxbId);
    }

    @DeleteMapping(value = "/courses")
    public ServerResponse cancelCS(@RequestParam(value = "token") String token, String jxbId) {
        return null;
    }
}
