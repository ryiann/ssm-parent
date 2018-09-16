package com.ryan.controller;

import com.ryan.pojo.ResponseEntity;
import com.ryan.service.StudentService;
import com.ryan.utils.BaseController;
import com.ryan.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

/**
 * @author YoriChen
 * @date 2018/5/21
 */
@Controller
public class StudentController extends BaseController {

    @Autowired
    StudentService studentService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/json/findStudentListByPage")
    public void findStudentListByPage(StudentVO stu){
        ResponseEntity<List<StudentVO>> stuList = studentService.findStudentListByPage(stu);
        writeJson(stuList);
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/json/findStudentInfoByStuId")
    public void findStudentInfoByStuId(Integer stuId){
        ResponseEntity<StudentVO> stuList = studentService.findStudentInfoByStuId(stuId);
        writeJson(stuList);
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/json/insertStudent")
    public void insertStudent(StudentVO stu){
        ResponseEntity<Object> resObj = studentService.insertStudent(stu);
        writeJson(resObj);
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/json/updateStudentByStuId")
    public void updateStudentByStuId(StudentVO stu){
        ResponseEntity<Object> resObj = studentService.updateStudentByStuId(stu);
        writeJson(resObj);
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/json/deleteStudentByStuId")
    public void deleteStudentByStuId(Integer stuId){
        ResponseEntity<Object> resObj = studentService.deleteStudentByStuId(stuId);
        writeJson(resObj);
    }
}