package com.ryan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ryan.dao.StudentMapper;
import com.ryan.enums.ResponseCode;
import com.ryan.pojo.Page;
import com.ryan.pojo.ResponseEntity;
import com.ryan.service.StudentService;
import com.ryan.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YoriChen
 * @date 2018/5/21
 */
@Service
public class StudentServiceImlp extends Page implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public ResponseEntity<List<StudentVO>> findStudentListByPage(StudentVO stu) {
        try {
            PageHelper.startPage(getPage(),getPageSize());

            List<StudentVO> studentList = studentMapper.findStudentListByPage(stu);

            PageInfo<StudentVO> pageInfo = new PageInfo<StudentVO>(studentList,getPageSize());
            // 获取总记录数
            long total = pageInfo.getTotal();
            return new ResponseEntity<>(studentList, total);
        }catch (Exception e){
            throw new RuntimeException(ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }

    @Override
    public ResponseEntity<StudentVO> findStudentInfoByStuId(Integer stuId) {
        return new ResponseEntity<>(studentMapper.findStudentInfoByStuId(stuId));
    }

    @Override
    public ResponseEntity<Object> insertStudent(StudentVO stu) {
        return new ResponseEntity<>(studentMapper.insertStudent(stu));
    }

    @Override
    public ResponseEntity<Object> updateStudentByStuId(StudentVO stu) {
        return new ResponseEntity<>(studentMapper.updateStudentByStuId(stu));
    }

    @Override
    public ResponseEntity<Object> deleteStudentByStuId(Integer stuId) {
        return new ResponseEntity<>(studentMapper.deleteStudentByStuId(stuId));
    }
}