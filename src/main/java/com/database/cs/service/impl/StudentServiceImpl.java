package com.database.cs.service.impl;

import com.database.cs.common.Constant;
import com.database.cs.common.ServerResponse;
import com.database.cs.dao.CourseSelectDao;
import com.database.cs.dao.JxbDao;
import com.database.cs.dao.StudentDao;
import com.database.cs.entity.CSelection;
import com.database.cs.entity.JXB;
import com.database.cs.service.StudentService;
import com.database.cs.util.JxbUtil;
import com.database.cs.vo.JxbVo;
import com.database.cs.vo.ReadyCourses;
import com.database.cs.vo.StudentCourseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao stuDao;

    @Autowired
    private JxbDao jxbDao;

    @Autowired
    private CourseSelectDao csDao;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private JxbServiceImpl jxbService;

    @Autowired
    private RedisLockServiceImpl redisLockService;

    /**
     * 获得可选课列表，按课程号分类
     */
    @Cacheable(value = "ready_courses")
    public ServerResponse getCourseList() {
        List<JXB> jxbList = jxbDao.getAll();
        return ServerResponse.createBySuccess(jxbsGroupByCourseCode(jxbList));
    }

    /**
     * 取消选课
     * @param stuId
     * @param jxbId
     * @return
     */
    @Transactional
    public ServerResponse cancelCourseSelect(String stuId, String jxbId) {
        // 查询是否选择了该课程
        CSelection cs = csDao.getOneByStuIdAndJxbId(stuId, jxbId);
        if (cs == null) return ServerResponse.createBySuccess();
        // 给redis加锁
        redisLockService.lock(Constant.CS_LOCK + jxbId, "1");
        if (!redisLockService.isLock()) return ServerResponse.createByErrorMessage("人数过多，请稍后尝试");
        // 给教学班现有人数-1， 如果redis缓存中数字低于0，置为1
        jxbDao.decrementCurrentNum(jxbId);
        csDao.cancelSelect(stuId, jxbId);
        String temp = redis.opsForValue().get(jxbId);
        int flag = Integer.valueOf(temp);
        if (flag <= 0)
            redis.opsForValue().set(jxbId, "1");
        // 解锁
        redisLockService.unlock();
        return ServerResponse.createBySuccess();
    }

    @Cacheable
    public ServerResponse getStudentCourses(String stuId, int week) {
        List<CSelection> csList = csDao.findByStuId(stuId);
        if (csList.size() > 0 ) {
            List<JXB> jxbList = new ArrayList<>();
            for (CSelection cSelection : csList) {
                JXB jxb = jxbDao.findByJxbIdAndYear(cSelection.getJxbId(), Constant.YEAR);
                jxbList.add(jxb);
            }
            if (jxbList.size() > 0) {
                List<StudentCourseVo> scList = getStudentCourseVo(jxbList, week);
                return ServerResponse.createBySuccess(scList);
            }
        }

        return ServerResponse.createBySuccess();
    }


    /**
     * 选课
     *@param stuId
     * @param jxbId
     * @return
     */
    @Transactional
    public ServerResponse courseSelect(String stuId, String jxbId) {
        JXB jxb = jxbService.getOne(jxbId).getData();
        if (jxb == null) return ServerResponse.createByErrorMessage("没有该课程可供选择");
        CSelection cSelection = csDao.getOneByStuIdAndCourseCode(stuId, jxb.getCourseCode());
        if (cSelection != null && cSelection.getStatus() == 1) return ServerResponse.createByErrorMessage("你已经选过该课程");

        if (jxb.getCurrentNum() >= jxb.getNumLimit()) return ServerResponse.createByErrorMessage("选课人数已达到上限");
        if (redis.hasKey(Constant.CS_LOCK + jxbId)) return ServerResponse.createByErrorMessage("人数过多，请稍后尝试");
        if (!redis.hasKey(jxbId)) {
            initJxbNumList(jxbId);
        }
        // 检查该教学班是否与学生已选的冲突
        List<CSelection> csList = csDao.findByStuId(stuId);
        if (csList.size() > 0) {
            List<JXB> jxbList = jxbDao.findByJxbIdIn(csList);
            if (JxbUtil.checkJxbConflict(jxbList, jxb).size() > 0)
                return ServerResponse.createByErrorMessage("与已选课程冲突");
        }

        // 获取redis锁

        long flag = redis.opsForValue().increment(jxbId, -1);
        if (flag > 0) {
            // 抢到号码 继续执行
            CSelection cs = new CSelection();
            cs.setJxbId(jxbId);
            cs.setStuId(stuId);
            cs.setCourseCode(jxb.getCourseCode());
            cs.setCreatedAt(new Date());
            cs.setUpdatedAt(new Date());
            cs.setYear(Constant.YEAR);
            cs.setStatus(Constant.CSelectStatus.CS.getCode());
            if (cSelection == null)
                csDao.save(cs);
            else
                csDao.recoverSelect(stuId, jxbId);
            jxbDao.incrementCurrentNum(jxbId);
            return ServerResponse.createBySuccess();
        }

        return ServerResponse.createByErrorMessage("选课人数已达到上限");
    }

    private void initJxbNumList(String jxbId) {
        JXB jxb = jxbDao.getOne(jxbId);
        if (jxb != null)
            redis.opsForValue().set(jxbId, String.valueOf(jxb.getNumLimit() - jxb.getCurrentNum()));
        else
            redis.opsForValue().set(jxbId, "0");
    }

    private List<ReadyCourses> jxbsGroupByCourseCode(List<JXB> jxbList) {
        List<ReadyCourses> list = new ArrayList<>();
        Map<String, List<JxbVo>> map = new HashMap<>();
        for (JXB jxb : jxbList) {
            JxbVo jxbVo = new JxbVo();
            BeanUtils.copyProperties(jxb, jxbVo);
            if (map.containsKey(jxb.getCourseCode()))
                map.get(jxb.getCourseCode()).add(jxbVo);
            else {
                List<JxbVo> jxbVos = new ArrayList<>();
                jxbVos.add(jxbVo);
                map.put(jxb.getCourseCode(), jxbVos);
            }
        }

        for (Map.Entry<String, List<JxbVo>> entry : map.entrySet()) {
            ReadyCourses rc = new ReadyCourses();
            rc.setCourseCode(entry.getKey());
            rc.setCourseName(entry.getValue().get(0).getJxbName());
            rc.setJxbVos(entry.getValue());
            list.add(rc);
        }
        return list;
    }

    private List<StudentCourseVo> getStudentCourseVo(List<JXB> jxbList, int week) {
        boolean flag = week != 0;
        List<StudentCourseVo> tcVoList = new ArrayList<>();
        for (JXB jxb : jxbList) {
            int[] weeks = weekToArray(jxb.getWeek());
            if (flag && !isInWeeks(weeks, week)) continue;
            StudentCourseVo scVo = new StudentCourseVo();
            BeanUtils.copyProperties(jxb, scVo);
            scVo.setWeek(weeks);
            tcVoList.add(scVo);
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
