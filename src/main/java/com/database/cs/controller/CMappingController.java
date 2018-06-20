package com.database.cs.controller;

import com.database.cs.common.ServerResponse;
import com.database.cs.entity.CMapping;
import com.database.cs.service.impl.CMapServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class CMappingController {

    @Autowired
    private CMapServiceImpl cmService;

    @GetMapping(value = "/cmaps")
    public ServerResponse getCMapList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
                                      @RequestParam(value = "key", required = false, defaultValue = "") String key) {
        return cmService.getMappingList(page, pageSize, key);
    }

    @PostMapping(value = "/cmaps")
    public ServerResponse saveNewCMap(@Valid CMapping cMapping, BindingResult res) {
        if (res.hasErrors())
            return ServerResponse.createByErrorMessage(res.getFieldError().getDefaultMessage());

        return cmService.saveNewCMapping(cMapping);
    }

    @DeleteMapping(value = "/cmaps/{cc}")
    public ServerResponse deleteCourseMapping(@PathVariable(value = "cc") String courseCode) {
        return cmService.deleteOneCMapping(courseCode);
    }
}
