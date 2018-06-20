package com.database.cs.service.impl;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 获得可选课列表，按课程号分类
     */
    public ServerResponse getCourseList() {
        List<JXB> jxbList = jxbDao.getAll();
        return ServerResponse.createBySuccess(jxbsGroupByCourseCode(jxbList));
    }


    /**
     * 选课
     *@param stuId
     * @param jxbId
     * @return
     */
    @Transactional
    public ServerResponse courseSelect(String stuId, String jxbId) {
        JXB jxb = jxbDao.getOne(jxbId);
        if (jxb == null) return ServerResponse.createByErrorMessage("没有该课程可供选择");
        CSelection cSelection = csDao.getOneByStuIdAndCourseCode(stuId, jxb.getCourseCode());
        if (cSelection != null) return ServerResponse.createByErrorMessage("你已经选过该课程");

        if (jxb.getCurrentNum() >= jxb.getNumLimit()) return ServerResponse.createByErrorMessage("人数已达到上限");
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

        int number = Integer.valueOf(redis.opsForList().rightPop(jxbId));
        if (number > 0) {
            // 抢到号码 继续执行
            CSelection cs = new CSelection();
            cs.setJxbId(jxbId);
            cs.setStuId(stuId);
            cs.setCourseCode(jxb.getCourseCode());
            cs.setCreatedAt(new Date());
            cs.setUpdatedAt(new Date());
            csDao.save(cs);
            JXB tempJxb = new JXB();
            tempJxb.setJxbId(jxbId);
            tempJxb.setCurrentNum(jxb.getCurrentNum() + 1);
            jxbDao.updateJxb(tempJxb);
            return ServerResponse.createBySuccess();
        }

        return ServerResponse.createByErrorMessage("抢课失败");
    }

    private void initJxbNumList(String jxbId) {
        JXB jxb = jxbDao.getOne(jxbId);
        String[] s = new String[jxb.getNumLimit()];
        for (Integer i = 1; i <= jxb.getNumLimit(); i++) {
            s[i - 1] = String.valueOf(i);
        }
        List<String> list = Arrays.asList(s);
        redis.opsForList().rightPushAll(jxbId, list);
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
}
