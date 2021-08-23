package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.UserDao;
import com.example.SESummer.Entity.User;
import com.example.SESummer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    //根据userName查找user
    public User getUserByUserName(String userName){
        return userDao.getUserByUserName(userName);
    }

    @Override
    //注册user
    public void registerUser(User user){
        userDao.registerUser(user);
    }

    @Override
    //判断用户名与密码是否相符
    public User getUserByUserNameAndPwd(String userName,String userPwd){
        return userDao.getUserByUserNameAndPwd(userName,userPwd);
    }

    @Override
    //虚假注册
    public void addFakeUser(String userName,String userPwd){
        userDao.addFakeUser(userName,userPwd);
    }
}
