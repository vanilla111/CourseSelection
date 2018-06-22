package com.database.cs.service.impl;

import com.database.cs.common.ServerResponse;
import com.database.cs.dao.AcademyDao;
import com.database.cs.entity.Academy;
import com.database.cs.service.AcademyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcademyServiceImpl implements AcademyService {

    @Autowired
    private AcademyDao acadDao;

    /**
     * 获得学院列表
     * @return
     */
    public ServerResponse getAll(String key) {
        if ("".equals(key))
            return ServerResponse.createBySuccess(acadDao.getAll());

        return ServerResponse.createBySuccess(acadDao.findByName(key));
    }

    /**
     * 新增学院分类
     * @param academy
     * @return
     */
    public ServerResponse save(Academy academy) {
        acadDao.save(academy);
        return ServerResponse.createBySuccess();
    }

    /**
     * 删除一个学院分类
     * @param id
     * @return
     */
    public ServerResponse delete(int id) {
        acadDao.deleteById(id);
        return ServerResponse.createBySuccess();
    }
}
