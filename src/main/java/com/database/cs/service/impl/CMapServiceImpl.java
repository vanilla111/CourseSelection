package com.database.cs.service.impl;

import com.database.cs.dao.CMappingDao;
import com.database.cs.entity.CMapping;
import com.database.cs.service.CMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CMapServiceImpl implements CMapService {

    @Autowired
    private CMappingDao cMappingDao;

    @Transactional
    public List<CMapping> getByCourseCode(String code) {
        return cMappingDao.getByCourseCode(code);
    }
}
