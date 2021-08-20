package com.example.SESummer.Service;

import com.example.SESummer.Entity.User;

public interface UserService {
    //根据userName查找user
    User getUserByUserName(String userName);

    //注册user
    void registerUser(User user);

    //判断用户名与密码是否相符
    User getUserByUserNameAndPwd(String userName,String userPwd);
}
