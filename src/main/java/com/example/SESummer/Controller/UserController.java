package com.example.SESummer.Controller;

import com.example.SESummer.Entity.User;
import com.example.SESummer.Service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "用户相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("注册用户的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",value="用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "userPwd",value="用户密码",required = true,dataType = "String"),
            @ApiImplicitParam(name = "reUserPwd",value="重新确认用户密码",required = true,dataType = "String")
    })
    public Map<String,Object> registerUser(@RequestParam String userName,@RequestParam String userPwd,@RequestParam String reUserPwd){
        Map<String, Object> map = new HashMap<>();
        try {
            if(userService.getUserByUserName(userName)!=null){
                map.put("success",false);
                map.put("message","用户名已存在");
            }
            else if(!reUserPwd.equals(userPwd)){
                map.put("success",false);
                map.put("message","两次密码输入不一致");
            }
            else{
                User user=new User();
                user.setUserName(userName);
                user.setUserPwd(userPwd);
                userService.registerUser(user);
                map.put("success",true);
                map.put("message","用户注册成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("message","用户注册失败");
        }
        return map;
    }

    @PostMapping("/login")
    @ApiOperation("登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",value="用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "userPwd",value="用户密码",required = true,dataType = "String")
    })
    public Map<String,Object> login(HttpServletRequest request, @RequestParam String userName,@RequestParam String userPwd){
        Map<String, Object> map = new HashMap<>();
        try {
            User user=userService.getUserByUserNameAndPwd(userName,userPwd);
            if (userService.getUserByUserName(userName) == null) {
                map.put("success", false);
                map.put("message", "用户名不存在");
            }
            else if(user==null){
                map.put("success",false);
                map.put("message","密码错误");
            }
            else{
                HttpSession session=request.getSession();
                session.setAttribute("userID",user.getUserID());
                map.put("success", true);
                map.put("message", "用户登录成功");
                map.put("user",user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "用户登录失败");
        }
        return map;
    }

    @PostMapping("/logout")
    @ApiOperation("注销接口")
    public Map<String,Object> logout (HttpServletRequest request){
        Map<String ,Object> map=new HashMap<>();
        try {
            HttpSession session=request.getSession();
            session.removeAttribute("userID");
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }
}
