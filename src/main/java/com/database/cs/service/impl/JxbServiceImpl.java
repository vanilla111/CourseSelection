package com.database.cs.service.impl;

import com.database.cs.common.Constant;
import com.database.cs.common.ServerResponse;
import com.database.cs.dao.*;
import com.database.cs.entity.CMapping;
import com.database.cs.entity.JXB;
import com.database.cs.entity.JxbOperateLog;
import com.database.cs.entity.Teacher;
import com.database.cs.service.JxbService;
import com.database.cs.util.JxbUtil;
import com.database.cs.vo.TeacherCourseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JxbServiceImpl implements JxbService {

    @Autowired
    private JxbDao jxbDao;

    @Autowired
    private TeacherDao teaDao;

    @Autowired
    private CMappingDao cmDao;

    @Autowired
    private JxbOperateDao joDao;

    @Autowired
    private CourseSelectDao csDao;

    /**
     * 根据教学班id获取
     * @param jxbId
     * @return
     */
    @Cacheable(value = "jxb", key = "#p0")
    public JXB getOne(String jxbId) {
        return jxbDao.getOne(jxbId);
    }

    /**
     * 根据教学班id获得选课学生列表
     * @param jxbId
     * @return
     */
    public ServerResponse getStuListByJxbId(String jxbId) {
        return ServerResponse.createBySuccess(csDao.findByJxbId(jxbId));
    }

    /**
     * 获得一个符合jxb特征的教学班列表
     * @param page
     * @param pageSize
     * @param jxb
     * @return
     */
    public ServerResponse<PageInfo<JXB>> getJxbList(int page, int pageSize, JXB jxb) {
        PageHelper.startPage(page, pageSize);
        List<JXB> jxbList = jxbDao.findByJxbContaining(jxb);

        return ServerResponse.createBySuccess(new PageInfo<>(jxbList));
    }

    /**
     * 根据课程号查找教学班
     * @param courseCode
     * @return
     */
    @Cacheable(value = "courseCodeToJxbs")
    public ServerResponse<List<JXB>> getJxbListByCourseCode(String courseCode) {
        JXB jxb = new JXB();
        jxb.setCourseCode(courseCode);
        return ServerResponse.createBySuccess(jxbDao.findByJxbContaining(jxb));
    }

    /**
     * 更新一个教学班的信息
     * @param jxb
     * @return
     */
    @Transactional
    public ServerResponse<String> updateOneJxb(JXB jxb) {
        if (jxb == null) return ServerResponse.createByErrorMessage("更新失败，空对象");
        if (jxb.getJxbId() == null) return ServerResponse.createByErrorMessage("缺少jxbId参数");
        if (jxb.getTeacherId() != null) {
            Teacher teacher = teaDao.getOne(jxb.getTeacherId());
            if (teacher == null) return ServerResponse.createByErrorMessage("教师工号不存在");
            jxb.setTeacher(teacher.getName());
        }
        JxbOperateLog jol = new JxbOperateLog();
        jol.setAdminId("admin");
        jol.setText("修改");
        jol.setCreatedAt(new Date());
        joDao.save(jol);
        jxbDao.updateJxb(jxb);

        return ServerResponse.createBySuccess();
    }

    /**
     * 获得教师课程表
     * @param teaId
     * @param week
     * @param year
     * @return
     */
    public ServerResponse getTeacherCourse(String teaId, int week, int year) {
        if (teaId == null) return ServerResponse.createByErrorMessage("未获得教师id，无法查询");
        week = week < 0 ? 0 : week;
        year = year < 0 ? Constant.YEAR : year;
        List<JXB> jxbList = jxbDao.findByTeaIdAndYear(teaId, year);
        List<TeacherCourseVo> tcVoList = getTeacherCourseVo(jxbList, week);
        return ServerResponse.createBySuccess(tcVoList);
    }

    /**
     * 新增一个教学班
     * @param jxb
     * @return
     */
    @Transactional
    public ServerResponse saveNewJxb(JXB jxb) {
        // 检查第几大节和持续时间 是否在合理范围内
        if (jxb.getHashLesson() < 0 || jxb.getPeriod() < 0 || jxb.getHashLesson() > 5 || jxb.getPeriod() > 12)
            return ServerResponse.createByErrorMessage("课程时间有误");

        // 检查人数限制
        if (jxb.getNumLimit() != null) {
            if (jxb.getNumLimit() <= 0) return ServerResponse.createByErrorMessage("人数限制不得非负");
        } else {
            jxb.setNumLimit(Constant.JXB_NUM_LIMIT);
        }

        // 检查周几是否在合理范围内
        if (jxb.getHashDay() < 0 || jxb.getHashDay() > 6)
            return ServerResponse.createByErrorMessage("周几数值有误");

        Teacher teacher = teaDao.getOne(jxb.getTeacherId());
        if (teacher == null || teacher.getName() == null) return ServerResponse.createByErrorMessage("该教师不存在");
        else jxb.setTeacher(teacher.getName());

        CMapping cm = cmDao.getOne(jxb.getCourseCode());
        if (cm == null) return ServerResponse.createByErrorMessage("该课程分类不存在");
        else jxb.setJxbName(cm.getCourseName());

        // 最后检查与其他教学班的冲突
        List<JXB> jxbList = jxbDao.findByClassRoomAndHashDayAndYear(jxb.getClassRoom(), jxb.getHashDay(), Constant.YEAR);
        List<JXB> conflictList = JxbUtil.checkJxbConflict(jxbList, jxb);
        if (conflictList.size() > 0)
            return ServerResponse.createByErrorMessageData("与已有的教学班在时间、地点上冲突", conflictList);

        jxb.setJxbId(JxbUtil.getNewJxbId(jxb.getCourseCode()));
        JxbUtil.timeFormat(jxb);
        jxbDao.saveNewJxb(jxb);
        JxbOperateLog jol = new JxbOperateLog();
        jol.setCreatedAt(new Date());
        jol.setAdminId("admin");
        jol.setText("create");
        joDao.save(jol);
        return ServerResponse.createBySuccess();
    }


    private List<TeacherCourseVo> getTeacherCourseVo(List<JXB> jxbList, int week) {
        boolean flag = week != 0;
        List<TeacherCourseVo> tcVoList = new ArrayList<>();
        for (JXB jxb : jxbList) {
            int[] weeks = weekToArray(jxb.getWeek());
            if (flag && !isInWeeks(weeks, week)) continue;
            TeacherCourseVo tcVo = new TeacherCourseVo();
            BeanUtils.copyProperties(jxb, tcVo);
            tcVo.setWeek(weeks);
            tcVoList.add(tcVo);
        }

        return tcVoList;
    }

    private int[] weekToArray(String week) {
        String[] chars = week.split(",");

        int[] weeks = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            weeks[i] = Integer.valueOf(chars[i]);
        }
        return weeks;
    }

    private boolean isInWeeks(int[] weeks, int week) {
        for (int i : weeks) {
            if (week == i) return true;
        }

        return false;
    }
}
