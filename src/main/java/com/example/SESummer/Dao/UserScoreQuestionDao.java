package com.example.SESummer.Dao;

import com.example.SESummer.Entity.UserScoreQuestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserScoreQuestionDao {
    //检索用户是否已评分
    UserScoreQuestion getScoreRecordByID(Integer userID, Integer questionnaireID, Integer questionContentID);

    //添加用户评分记录
    void addScoreRecord(UserScoreQuestion score);

    //修改用户评分记录
    void updateScoreRecord(Integer userID, Integer questionnaireID, Integer questionContentID,Integer score);

    //删除用户填写该问卷的所有评分记录
    void delRecord(Integer userID,Integer questionnaireID);

    //获取评分题分数分布
    Integer sumOfScore(Integer questionContentID,Integer i);
}
