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
    public UserScoreQuestion getScoreRecordByID(Integer userID,Integer questionnaireID,Integer questionContentID){
        return userScoreQuestionDao.getScoreRecordByID(userID,questionnaireID,questionContentID);
    }

    @Override
    public void addScoreRecord(UserScoreQuestion score){
        userScoreQuestionDao.addScoreRecord(score);
    }

    @Override
    public void updateScoreRecord(UserScoreQuestion score){
        userScoreQuestionDao.updateScoreRecord(score);
    }
}
