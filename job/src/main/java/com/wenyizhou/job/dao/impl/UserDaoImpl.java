package com.wenyizhou.job.dao.impl;

import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.mapping.StudentMapping;
import com.wenyizhou.job.mapping.UserMapping;
import com.wenyizhou.job.model.Student;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.StudentVO;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserDaoImpl implements IUserDao {
    @Resource
    UserMapping userMapping;
    @Resource
    StudentMapping studentMapping;

    @Override
    public boolean register(User user) throws Exception{
        return userMapping.insert(user);
    }

    @Override
    public User login(String userPhone, String userPassword) {
        Map m = new HashMap();
        m.put("userPhone",userPhone);
        m.put("userPassword",userPassword);
        return userMapping.select(m);
    }

    @Override
    public User selectUserById(String userId) {
        return userMapping.selectUserById(userId);
    }

    @Override
    public StudentVO selectStudentById(String userId) {
        return studentMapping.selectStudentById(userId);
    }

    @Override
    public boolean changeInfo(User user) {
        try {
            userMapping.updateInfo(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
