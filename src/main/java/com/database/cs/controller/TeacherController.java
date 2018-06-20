package com.database.cs.controller;

import com.database.cs.common.ServerResponse;
import com.database.cs.service.impl.JxbServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private JxbServiceImpl jxbService;

    @GetMapping(value = "/courses/{tid}")
    public ServerResponse getTeacherCourse(@PathVariable(value = "tid") String teaId,
            @RequestParam(value = "week", required = false, defaultValue = "0") int week) {
        return jxbService.getTeacherCourse(teaId, week, -1);
    }
}
