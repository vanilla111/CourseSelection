package com.database.cs.service.impl;

import com.database.cs.common.ServerResponse;
import com.database.cs.dao.TAcademyDao;
import com.database.cs.dao.TeacherDao;
import com.database.cs.entity.TAcademy;
import com.database.cs.entity.Teacher;
import com.database.cs.service.ATeacherService;
import com.database.cs.util.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ATeacherServiceImpl implements ATeacherService {

    @Autowired
    private TeacherDao tDao;

    @Autowired
    private TAcademyDao taDao;

    /**
     * 获取教师列表
     * @param page
     * @param pageSize
     * @param key
     * @return
     */
    public ServerResponse<PageInfo> getTeacherList(int page, int pageSize, String key) {
        PageHelper.startPage(page, pageSize);
        List<Teacher> tList = new ArrayList<>();
        if ("".equals(key))
            tList = tDao.getAll();
        else
            tList = tDao.findByTeaIdContainingOrNameContaining(key);

        PageInfo pageInfo = new PageInfo<Teacher>(tList);

        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 新增一名教师
     * @param teacher
     * @return
     */
    public ServerResponse<String> addNewTeacher(Teacher teacher) {
        if (teacher == null ) return ServerResponse.createByErrorMessage("添加失败");
        TAcademy ta = taDao.getOne(teacher.getAid());
        if (ta == null) return ServerResponse.createByErrorMessage("院系代号错误");
        else teacher.setMajor(ta.getAcadName());
        Teacher t = tDao.getOne(teacher.getTeaId());
        if (t != null) return ServerResponse.createByErrorMessage("该工号已被占用");
        teacher.setPassword(MD5Util.MD5EncodeUtf8(teacher.getPassword()));
        if (tDao.save(teacher))
            return ServerResponse.createBySuccess();

        return ServerResponse.createByErrorMessage("添加失败");
    }

    /**
     * 更改一个教师的资料
     * @param teacher
     * @return
     */
    public ServerResponse<String> updateTeacher(Teacher teacher) {
        Teacher t = tDao.getOne(teacher.getTeaId());
        if (t == null) return ServerResponse.createByErrorMessage("教师工号错误");
        setUpdateTeacher(t, teacher);
        TAcademy ta = taDao.getOne(teacher.getAid());
        if (ta == null) return ServerResponse.createByErrorMessage("院系代号错误");
        else teacher.setMajor(ta.getAcadName());

        if (tDao.update(teacher))
            return ServerResponse.createBySuccess();

        return ServerResponse.createByErrorMessage("更新失败");
    }

    /**
     * 删除一名教师，置status=0
     * @param teaId
     * @return
     */
    public ServerResponse<String> delTeacher(String teaId) {
        if (teaId == null) return ServerResponse.createByErrorMessage("删除失败");
        if (tDao.delete(teaId))
            return ServerResponse.createBySuccess();

        return ServerResponse.createByErrorMessage("删除失败");
    }


    private void setUpdateTeacher(Teacher target, Teacher temp) {
        if (temp.getName() != null) target.setName(temp.getName());
        if (temp.getAid() != null) target.setAid(temp.getAid());
        if (temp.getPassword() != null) target.setPassword(MD5Util.MD5EncodeUtf8(temp.getPassword()));
        if (temp.getPosition() != null) target.setPosition(temp.getPosition());
    }

}
