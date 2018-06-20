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

    @GetMapping("/courses")
    public ServerResponse getReadyCourses() {
        return stuService.getCourseList();
    }

    @PostMapping("/cs.do")
    public ServerResponse courseSelect(@RequestParam(value = "token") String token, String jxbId) {
        return stuService.courseSelect(token, jxbId);
    }
}
