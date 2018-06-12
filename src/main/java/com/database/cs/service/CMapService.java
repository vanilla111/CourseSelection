package com.database.cs.service;

import com.database.cs.entity.CMapping;

import java.util.List;

public interface CMapService {
    public List<CMapping> getByCourseCode(String code);
}
