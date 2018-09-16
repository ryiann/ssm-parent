package com.ryan.dao;

import com.ryan.vo.StudentVO;
import java.util.List;

/**
 * @author YoriChen
 * @date 2018/5/21
 */
public interface StudentMapper {

    List<StudentVO> findStudentListByPage(StudentVO stu);

    StudentVO findStudentInfoByStuId(Integer stuId);

    int insertStudent(StudentVO stu);

    int updateStudentByStuId(StudentVO stu);

    int deleteStudentByStuId(Integer stuId);

}