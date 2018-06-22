package com.database.cs.controller;

import com.database.cs.common.ServerResponse;
import com.database.cs.entity.Academy;
import com.database.cs.service.impl.AcademyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/admin")
public class AcademyController {

    @Autowired
    private AcademyServiceImpl academyService;

    @GetMapping(value = "/academies")
    public ServerResponse getAll(@RequestParam(value = "key", required = false, defaultValue = "")String key) {
        return academyService.getAll(key);
    }

    @PostMapping(value = "/academies")
    public ServerResponse addNewOne(@Valid Academy academy, BindingResult res) {
        if (res.hasErrors())
            return ServerResponse.createByErrorMessage(res.getFieldError().getDefaultMessage());

        return academyService.save(academy);
    }

    @DeleteMapping(value = "/academies/{id}")
    public ServerResponse deleteOne(@PathVariable(value = "id") int id) {
        return academyService.delete(id);
    }
}
