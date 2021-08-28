package com.example.SESummer.Dao;

import com.example.SESummer.Entity.UserLocateQuestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLocateQuestionDao {
    //检索用户是否答过该定位题
    UserLocateQuestion getLocateRecordByID(Integer userID, Integer questionnaireID, Integer questionContentID);

    //添加用户定位记录
    void addLocateRecord(UserLocateQuestion userLocateQuestion);

    //修改用户定位记录
    void updateLocateRecord(Integer userID,Integer questionnaireID,Integer questionContentID,String locate);
}
