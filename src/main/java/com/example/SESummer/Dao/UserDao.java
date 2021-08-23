package com.example.SESummer.Dao;

import com.example.SESummer.Entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    //根据userName查找user
    User getUserByUserName(String userName);

    //注册user
    void registerUser(User user);

    //判断用户名与密码是否相符
    User getUserByUserNameAndPwd(String userName,String userPwd);

    //虚假注册
    void addFakeUser(String userName,String userPwd);
}
