package com.database.cs.service.impl;

import com.database.cs.common.RoleEnum;
import com.database.cs.common.ServerResponse;
import com.database.cs.dao.StudentDao;
import com.database.cs.dao.UserDao;
import com.database.cs.entity.Student;
import com.database.cs.entity.User;
import com.database.cs.service.AStudentService;
import com.database.cs.util.MD5Util;
import com.database.cs.vo.StudentVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AStudentServiceImpl implements AStudentService {

    @Autowired
    private StudentDao stuDao;

    @Autowired
    private UserDao userDao;

    /**
     * 获得某个学生的具体信息
     * @param stuId
     * @return
     */
    public ServerResponse<StudentVo> getOneStudent(String stuId) {
        Student stu = stuDao.getOne(stuId);
        StudentVo stuVo = new StudentVo();
        BeanUtils.copyProperties(stu, stuVo);
        return ServerResponse.createBySuccess(stuVo);
    }

    /**
     * 根据学生信息查询符合特征的学生列表
     * @param page
     * @param pageSize
     * @param stu
     * @return
     */
    public ServerResponse<PageInfo<StudentVo>> getStuList(int page, int pageSize, Student stu) {
        PageHelper.startPage(page, pageSize);
        List<Student> students = stuDao.findByStudentContaining(stu);
        List<StudentVo> studentVos = stuToStuVo(students);
        PageInfo<StudentVo> pageInfo = new PageInfo<>(studentVos);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 新增一名学生
     * @param student
     * @return
     */
    @Transactional
    public ServerResponse<String> addNewStudent(Student student) {
        if (student == null) return ServerResponse.createByErrorMessage("添加失败");
        User user = userDao.getOne(student.getStuId());
        if (user != null) return ServerResponse.createByErrorMessage("该学号或工号已存在");
        user = new User();
        user.setUserName(student.getStuId());
        user.setPassword(MD5Util.MD5EncodeUtf8(student.getPassword()));
        user.setRole(RoleEnum.STUDENT.getCode());
        userDao.save(user);
        stuDao.save(student);

        return ServerResponse.createBySuccess();
    }

    /**
     * 更改一个学生的信息
     * @param student
     * @return
     */
    @Transactional
    public ServerResponse<String> updateOne(Student student) {
        User user = userDao.getOne(student.getStuId());
        if (user == null) return ServerResponse.createByErrorMessage("该学生不存在");
        if (student.getPassword() != null) {
            user.setPassword(MD5Util.MD5EncodeUtf8(student.getPassword()));
            userDao.updateOne(user);
        }
        stuDao.updateOne(student);

        return ServerResponse.createBySuccess();
    }

    /**
     * 删除一个学生（置status=0）
     * @param stuId
     * @return
     */
    public ServerResponse<String> deleteOne(String stuId) {
        if (stuId == null) return ServerResponse.createByError();
        if (stuDao.delete(stuId))
            return ServerResponse.createBySuccess();

        return ServerResponse.createByError();
    }

    private List<StudentVo> stuToStuVo(List<Student> students) {
        List<StudentVo> studentVos = new ArrayList<>();
        for (Student student : students) {
            StudentVo studentVo = new StudentVo();
            BeanUtils.copyProperties(student, studentVo);
            studentVos.add(studentVo);
        }
        return studentVos;
    }
}
