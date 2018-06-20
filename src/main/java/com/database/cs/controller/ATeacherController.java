package com.database.cs.controller;

import com.database.cs.common.ServerResponse;
import com.database.cs.entity.Teacher;
import com.database.cs.service.impl.ATeacherServiceImpl;
import com.database.cs.vo.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class ATeacherController {

    @Autowired
    private ATeacherServiceImpl atService;

    @GetMapping(value = "/teachers")
    public ServerResponse getList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
                                @RequestParam(value = "key", required =  false, defaultValue = "") String key) {
        return atService.getTeacherList(page, pageSize, key);
    }

    @GetMapping(value = "/teachers/{tid}")
    public ServerResponse<TeacherVo> getOneTeacher(@PathVariable(value = "tid") String teaId) {
        return atService.getOneTeacher(teaId);
    }

    @PostMapping(value = "/teachers")
    public ServerResponse addTeacher(@Valid Teacher teacher, BindingResult res) throws Exception {
        if (res.hasErrors())
            return ServerResponse.createByErrorMessage(res.getFieldError().getDefaultMessage());

        return atService.addNewTeacher(teacher);
    }

    @PutMapping(value = "/teachers/{tid}")
    public ServerResponse updateTeacher(@PathVariable(value = "tid") String tid,
                                        @RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "aid", required = false) String aid,
                                        @RequestParam(value = "position", required = false) String position,
                                        @RequestParam(value = "password", required = false) String password){
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setAid(aid);
        teacher.setPosition(position);
        teacher.setPassword(password);
        return atService.updateTeacher(teacher);
    }

    @DeleteMapping(value = "/teachers/{tid}")
    public ServerResponse delTeacher(@PathVariable(value = "tid") String tid) {
        return atService.delTeacher(tid);
    }
}
