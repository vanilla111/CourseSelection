package com.database.cs.controller;

import com.database.cs.common.ServerResponse;
import com.database.cs.entity.Student;
import com.database.cs.service.impl.AStudentServiceImpl;
import com.database.cs.vo.StudentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AStudentController {

    @Autowired
    private AStudentServiceImpl asService;

    @GetMapping("/students")
    public ServerResponse getList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
                                  @Valid StudentVo stuVo, BindingResult res) throws Exception {
        if (res.hasErrors())
            return ServerResponse.createByErrorMessage(res.getFieldError().getDefaultMessage());
        Student student = new Student();
        BeanUtils.copyProperties(stuVo, student);
        return asService.getStuList(page, pageSize, student);
    }

    @GetMapping("/students/{sid}")
    public ServerResponse getOne(@PathVariable(value = "sid") String stuId) {
        return asService.getOneStudent(stuId);
    }

    @PostMapping("/students")
    public ServerResponse addNewStudent(@Valid Student student, BindingResult res) {
        if (res.hasErrors())
            return ServerResponse.createByErrorMessage(res.getFieldError().getDefaultMessage());

        return asService.addNewStudent(student);
    }

    @PutMapping("/students/{sid}")
    public ServerResponse updateStudentInfo(@PathVariable(value = "sid") String stuId,
                                            Student student) {
        student.setStuId(stuId);
        return asService.updateOne(student);
    }

    @DeleteMapping("/students/{sid}")
    public ServerResponse deleteOneStudent(@PathVariable(value = "sid") String stuId) {
        return asService.deleteOne(stuId);
    }
}
