package com.example.SESummer.Dao;

import com.example.SESummer.Entity.UserLocateQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserLocateQuestionDao {
    //检索用户是否答过该定位题
    UserLocateQuestion getLocateRecordByID(Integer userID, Integer questionnaireID, Integer questionContentID);

    //添加用户定位记录
    void addLocateRecord(UserLocateQuestion userLocateQuestion);

    //修改用户定位记录
    void updateLocateRecord(Integer userID,Integer questionnaireID,Integer questionContentID,String locate);

    //删除用户填写该问卷的所有定位记录
    void delRecord(Integer userID,Integer questionnaireID);

    //获取定位题的所有填写情况
    List<UserLocateQuestion> getAllLocateRecordOfQuestion(Integer questionContentID);
}
