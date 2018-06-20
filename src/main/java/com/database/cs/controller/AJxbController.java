package com.database.cs.controller;

import com.database.cs.common.ServerResponse;
import com.database.cs.entity.JXB;
import com.database.cs.service.impl.JxbServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AJxbController {

    @Autowired
    private JxbServiceImpl jxbService;

    @GetMapping(value = "/jxbs")
    public ServerResponse getList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
                                  JXB jxb) {
        return jxbService.getJxbList(page, pageSize, jxb);
    }

    @GetMapping(value = "/jxbs/{cc}")
    public ServerResponse getListByCourseCode(@PathVariable(value = "cc") String courseCode){
        return jxbService.getJxbListByCourseCode(courseCode);
    }

    @PostMapping(value = "/jxbs")
    public ServerResponse addNewJxb(@Valid JXB jxb, BindingResult res) {
        if (res.hasErrors())
            return ServerResponse.createByErrorMessage(res.getFieldError().getDefaultMessage());

        return jxbService.saveNewJxb(jxb);
    }

    @PutMapping(value = "/jxbs/{jid}")
    public ServerResponse updateJxb(@PathVariable(value = "jid") String jid,
                                    JXB jxb) {
        jxb.setJxbId(jid);
        return jxbService.updateOneJxb(jxb);
    }
}
