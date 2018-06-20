package com.database.cs.service.impl;

import com.database.cs.common.Constant;
import com.database.cs.common.ServerResponse;
import com.database.cs.dao.CMappingDao;
import com.database.cs.dao.JxbDao;
import com.database.cs.dao.JxbOperateDao;
import com.database.cs.dao.TeacherDao;
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

    /**
     * 获得一个符合jxb特征的教学班列表
     * @param page
     * @param pageSize
     * @param jxb
     * @return
     */
    public ServerResponse<PageInfo<JXB>> getJxbList(int page, int pageSize, JXB jxb) {
        PageHelper.startPage(page, pageSize);

        return null;
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
        jol.setWho("admin");
        jol.setDesc("create");
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
