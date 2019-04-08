package com.wenyizhou.job.service.impl;

import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.Student;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.StudentVO;
import com.wenyizhou.job.service.IUserService;
import com.wenyizhou.job.utils.ErrorCode;
import com.wenyizhou.job.utils.IDUtil;
import com.wenyizhou.job.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IUserDao userDao;
    private final static int RESPONSE_SUCCESS = 200;

    @Override
    public Response login(String userPhone, String userPassword) {
        //参数判空
        Response response = new Response();
        if(StringUtils.isEmpty(userPhone)||StringUtils.isEmpty(userPassword)||userPhone.length() != 11){
            response.setError(ErrorCode.PARAMETER_ERROR);
        }else {
            User user = userDao.login(userPhone,userPassword);
            //将数据存入session
            if(user != null){
                httpServletRequest.getSession().setAttribute("user",user);
                response.setStatus(RESPONSE_SUCCESS);
                response.setData(user);
                response.setMsg("登录成功");
            }else {
                response.setError(ErrorCode.ACCOUNT_PASSWORD_ERROR);
            }
        }
        return response;
    }

    @Override
    public Response register(User user) {
        Response response = new Response();
        if(user == null){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //封装id，registertime
        user.setUserId(IDUtil.getUserId());
        user.setRegisterTime(TimeUtil.getTime());
        try {
            if(userDao.register(user)){
                response.setStatus(RESPONSE_SUCCESS);
                //response.setData(userDao.selectUserById(userId));
                response.setMsg("注册成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setData(e.getMessage());
            response.setStatus(3000);
        }

        return response;
    }

    @Override
    public Response userInfo(String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //先在user表查询登录信息
        User user = userDao.selectUserById(userId);
        if(user == null){
            response.setError(ErrorCode.ACCOUNT_NOT_EXIST);
            return response;
        }
        //如果session没有user对象,存入session中
        if(httpServletRequest.getSession().getAttribute("user") == null){
            httpServletRequest.getSession().setAttribute("user",user);
        }
        switch (user.getRoleType()){
            case 0:
                StudentVO student = userDao.selectStudentById(userId);
                if(student == null){
                    response.setError(ErrorCode.DATA_NOT_EXIST);
                    return response;
                }
                response.setStatus(RESPONSE_SUCCESS);
                response.setData(student);
                response.setMsg("查询成功");
                httpServletRequest.getSession().setAttribute("student",student);
                break;
            case 1:break;
            case 2:break;
            default:break;
        }
        return response;
    }

    @Override
    public Response changeInfo(User user) {
        Response response = new Response();
        if(user ==  null){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        if(userDao.changeInfo(user)){
            response.setStatus(RESPONSE_SUCCESS);
            response.setMsg("修改基本信息成功");
        }else {
            response.setError(ErrorCode.SYSTEM_EXCEPTION);
        }
        return response;
    }

    @Override
    public Response exit() {
        System.out.println("---exit1---");
        if(httpServletRequest.getSession().getAttribute("user") != null){
            System.out.println("---user1---");
            httpServletRequest.getSession().removeAttribute("user");
        }
        if(httpServletRequest.getSession().getAttribute("student") != null){
            System.out.println("---student1---");
            httpServletRequest.getSession().removeAttribute("student");
        }
        Response response = new Response();
        response.setStatus(RESPONSE_SUCCESS);
        return response;
    }


}