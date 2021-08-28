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
    public List<QuestionContent> getQuestionsByQuestionnaireID(Integer questionnaireID){
        return dataOutputDao.getQuestionsByQuestionnaireID(questionnaireID);
    }

//    @Override
//    public List<QuestionOption> getQuestionOptionByQuestionContentID(Integer userID,Integer questionContentID){
//        return dataOutputDao.getQuestionOptionByQuestionContentID(userID,questionContentID);
//    }

    @Override
    public UserChooseQuestion getChooseByID(Integer userID,Integer questionContentID){
        return dataOutputDao.getChooseByID(userID,questionContentID);
    }

    @Override
    public UserCompletionQuestion getCompletionByID(Integer userID,Integer questionContentID){
            return dataOutputDao.getCompletionByID(userID,questionContentID);
    }

    @Override
    public UserScoreQuestion getScoreByID(Integer userID,Integer questionContentID){
        return dataOutputDao.getScoreByID(userID,questionContentID);
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
    public UserChooseQuestion getOptionByOptionID(Integer userid,Integer questionContentID,Integer questionOptionID){
        return dataOutputDao.getOptionByOptionID(userid,questionContentID,questionOptionID);
    }

    @Override
    public List<QuestionContent> getChooseContentByID(Integer questionnaireID){
        return dataOutputDao.getChooseContentByID(questionnaireID);
    }

    @Override
    public List<QuestionContent> getCompletionContentByID(Integer questionnaireID){
        return dataOutputDao.getCompletionContentByID(questionnaireID);
    }

    @Override
    public List<QuestionContent> getScoreContentByID(Integer questionnaireID){
        return dataOutputDao.getScoreContentByID(questionnaireID);
    }

    @Override
    public QuestionContent getQuestionContentByID(Integer questionContentID){
        return dataOutputDao.getQuestionContentByID(questionContentID);
    }

    @Override
    public List<UserChooseQuestion> getChooseList(Integer questionContentID){
        return dataOutputDao.getChooseList(questionContentID);
    }

    @Override
    public List<UserCompletionQuestion> getCompletionList(Integer questionContentID){
        return dataOutputDao.getCompletionList(questionContentID);
    }

    @Override
    public List<UserScoreQuestion> getScoreList(Integer questionContentID){
        return dataOutputDao.getScoreList(questionContentID);
    }
}
