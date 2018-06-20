package com.database.cs.service.impl;

import com.database.cs.common.ServerResponse;
import com.database.cs.dao.CMappingDao;
import com.database.cs.dao.JxbDao;
import com.database.cs.entity.CMapping;
import com.database.cs.entity.JXB;
import com.database.cs.service.CMapService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CMapServiceImpl implements CMapService {

    @Autowired
    private CMappingDao cMappingDao;

    @Autowired
    private JxbDao jxbDao;

    /**
     * 根据字符串查找符合的课程信息
     * @param page
     * @param pageSize
     * @param str
     * @return
     */
    public ServerResponse<PageInfo> getMappingList(int page, int pageSize, String str) {
        PageHelper.startPage(page, pageSize);
        List<CMapping> cMappings;
        if ("".equals(str))
            cMappings = cMappingDao.getAll();
        else
            cMappings = cMappingDao.findByCodeContainingOrNameContaining(str);
        PageInfo pageInfo = new PageInfo<CMapping>(cMappings);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 新增一个课程分类
     * @param cMapping
     * @return
     */
    public ServerResponse<String> saveNewCMapping(CMapping cMapping) {
        // 课程号一样的不允许添加
        CMapping cm = cMappingDao.getOne(cMapping.getCourseCode());
        if (cm != null)
            return ServerResponse.createByErrorMessage("已存在该课程分类，课程号" + cm.getCourseCode() +
                    ":" + cm.getCourseName() + "重复");

        if (cMappingDao.save(cMapping)) {
            return ServerResponse.createBySuccess();
        } else
            return ServerResponse.createByErrorMessage("保存失败");
    }

    /**
     * 删除一个课程分类
     * @param courseCode
     * @return
     */
    public ServerResponse<String> deleteOneCMapping(String courseCode) {
        JXB jxb = jxbDao.getOneJxbByCourseCode(courseCode);
        if (jxb != null) return ServerResponse.createByErrorMessage("该分类下尚有教学班，不可删除");
        if (cMappingDao.delete(courseCode)) return ServerResponse.createBySuccess();

        return ServerResponse.createByErrorMessage("删除失败");
    }
}
