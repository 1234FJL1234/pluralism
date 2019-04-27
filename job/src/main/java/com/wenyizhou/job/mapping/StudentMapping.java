package com.wenyizhou.job.mapping;

import com.wenyizhou.job.model.Student;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.StudentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapping {

    StudentVO selectStudentById(String userId);

    boolean updateIntroduction(Map m);

    boolean updateJobType(Map m);

    void updateInfo(Student student);

    void delectFreeTime(String freId);

    void addFreeTime(Map m);

    void insertStudentInfo(Student stu);

    List<StudentVO> selectStudents(Integer page);

    Student getUserInfoById(String stuId);

    void updateStudentInfo(Student student);

    int selectStudentPage();

    List<StudentVO> selectStudentList(Integer page);
}
