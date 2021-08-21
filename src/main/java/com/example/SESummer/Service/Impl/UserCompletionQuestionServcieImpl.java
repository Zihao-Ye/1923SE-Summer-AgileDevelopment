package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.UserCompletionQuestionDao;
import com.example.SESummer.Entity.UserCompletionQuestion;
import com.example.SESummer.Service.UserCompletionQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCompletionQuestionServcieImpl implements UserCompletionQuestionService {
    @Autowired
    private UserCompletionQuestionDao userCompletionQuestionDao;

    @Override
    public UserCompletionQuestion getCompletionRecordByID(Integer userID,Integer questionnaireID,Integer questionContentID){
        return userCompletionQuestionDao.getCompletionRecordByID(userID,questionnaireID,questionContentID);
    }

    @Override
    public void addCompletionRecord(UserCompletionQuestion completion){
        userCompletionQuestionDao.addCompletionRecord(completion);
    }

    @Override
    public void updateCompletionRecord(UserCompletionQuestion completion){
        userCompletionQuestionDao.updateCompletionRecord(completion);
    }
}
