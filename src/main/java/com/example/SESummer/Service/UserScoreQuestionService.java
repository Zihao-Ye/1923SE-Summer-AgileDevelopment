package com.example.SESummer.Service;

import com.example.SESummer.Entity.UserScoreQuestion;

public interface UserScoreQuestionService {
    //检索是否已评分
    UserScoreQuestion getScoreRecordByID(Integer userID,Integer questionnaireID,Integer questionContentID);

    //添加用户评分记录
    void addScoreRecord(UserScoreQuestion score);

    //修改用户评分记录
    void updateScoreRecord(UserScoreQuestion score);
}
