package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.UserScoreQuestionDao;
import com.example.SESummer.Entity.UserScoreQuestion;
import com.example.SESummer.Service.UserScoreQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserScoreQuestionServiceImpl implements UserScoreQuestionService {
    @Autowired
    private UserScoreQuestionDao userScoreQuestionDao;

    @Override
    //检索用户是否已评分
    public UserScoreQuestion getScoreRecordByID(Integer userID,Integer questionnaireID,Integer questionContentID){
        return userScoreQuestionDao.getScoreRecordByID(userID,questionnaireID,questionContentID);
    }

    @Override
    //添加用户评分记录
    public void addScoreRecord(UserScoreQuestion score){
        userScoreQuestionDao.addScoreRecord(score);
    }

    @Override
    //修改用户评分记录
    public void updateScoreRecord(Integer userID,Integer questionnaireID,Integer questionContentID,Integer score){
        userScoreQuestionDao.updateScoreRecord(userID,questionnaireID,questionContentID,score);
    }

    @Override
    //删除用户填写该问卷的所有评分记录
    public void delRecord(Integer userID,Integer questionnaireID){
        userScoreQuestionDao.delRecord(userID,questionnaireID);
    }
}
