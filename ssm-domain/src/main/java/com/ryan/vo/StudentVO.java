package com.ryan.vo;

import java.io.Serializable;

/**
 * @author YoriChen
 * @date 2018/5/21
 */
public class StudentVO implements Serializable {

    /**编号 */
    private Integer stuId;

    /**学号 */
    private Integer stuNumber;

    /**姓名 */
    private String stuName;

    /**年龄 */
    private Integer stuAge;

    /**性别 */
    private String stuGender;

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(Integer stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getStuAge() {
        return stuAge;
    }

    public void setStuAge(Integer stuAge) {
        this.stuAge = stuAge;
    }

    public String getStuGender() {
        return stuGender;
    }

    public void setStuGender(String stuGender) {
        this.stuGender = stuGender;
    }

    @Override
    public String toString() {
        return "StudentVO{" +
                "stuId=" + stuId +
                ", stuNumber=" + stuNumber +
                ", stuName='" + stuName + '\'' +
                ", stuAge=" + stuAge +
                ", stuGender='" + stuGender + '\'' +
                '}';
    }
}