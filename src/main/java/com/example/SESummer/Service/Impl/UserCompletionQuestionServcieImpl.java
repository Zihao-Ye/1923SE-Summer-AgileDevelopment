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
    //检索用户是否回答过该填空题
    public UserCompletionQuestion getCompletionRecordByID(Integer userID,Integer questionnaireID,Integer questionContentID){
        return userCompletionQuestionDao.getCompletionRecordByID(userID,questionnaireID,questionContentID);
    }

    @Override
    //添加用户填空题答题记录
    public void addCompletionRecord(Integer userID, Integer questionnaireID, Integer questionContentID, String completionContent){
        userCompletionQuestionDao.addCompletionRecord(userID,questionnaireID,questionContentID,completionContent);
    }

    @Override
    //修改用户填空题答题记录
    public void updateCompletionRecord(Integer userID, Integer questionnaireID, Integer questionContentID,String questionContent){
        userCompletionQuestionDao.updateCompletionRecord(userID,questionnaireID,questionContentID,questionContent);
    }
}
