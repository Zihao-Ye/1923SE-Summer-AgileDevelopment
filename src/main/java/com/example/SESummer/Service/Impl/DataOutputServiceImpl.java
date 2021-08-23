package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.DataOutputDao;
import com.example.SESummer.Entity.*;
import com.example.SESummer.Service.DataOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataOutputServiceImpl implements DataOutputService {
    @Autowired
    private DataOutputDao dataOutputDao;

    @Override
    public List<QuestionContent> getQuestionsByQuestionnaireID(Integer userID,Integer questionnaireID){
        return dataOutputDao.getQuestionsByQuestionnaireID(userID,questionnaireID);
    }

//    @Override
//    public List<QuestionOption> getQuestionOptionByQuestionContentID(Integer userID,Integer questionContentID){
//        return dataOutputDao.getQuestionOptionByQuestionContentID(userID,questionContentID);
//    }

    @Override
    public List<UserChooseQuestion> getChooseByID(Integer userID,Integer questionnaireID){
        return dataOutputDao.getChooseByID(userID,questionnaireID);
    }

    @Override
    public List<UserCompletionQuestion> getCompletionByID(Integer userID,Integer questionnaireID){
        return dataOutputDao.getCompletionByID(userID,questionnaireID);
    }

    @Override
    public List<UserScoreQuestion> getScoreByID(Integer userID,Integer questionnaireID){
        return dataOutputDao.getScoreByID(userID,questionnaireID);
    }

    @Override
    public String getUserNameByUserID(Integer userID){
        return dataOutputDao.getUserNameByUserID(userID);
    }

    @Override
    public List<QuestionnaireSubmit> getUserIDByQuestionnaireID(Integer questionnaireID){
        return dataOutputDao.getUserIDByQuestionnaireID(questionnaireID);
    }

    @Override
    public String getTitleByQuestionnaireID(Integer questionnaireID){
        return dataOutputDao.getTitleByQuestionnaireID(questionnaireID);
    }

    @Override
    public String getContentByQuestionID(Integer questionContentID){
        return dataOutputDao.getContentByQuestionID(questionContentID);
    }

    @Override
    public List<QuestionOption> getAllOptionsByQuestionID(Integer questionContentID){
        return dataOutputDao.getAllOptionsByQuestionID(questionContentID);
    }

    @Override
    public List<UserChooseQuestion> getOptionByOptionID(Integer userid,Integer questionOptionID){
        return dataOutputDao.getOptionByOptionID(userid,questionOptionID);
    }
}
