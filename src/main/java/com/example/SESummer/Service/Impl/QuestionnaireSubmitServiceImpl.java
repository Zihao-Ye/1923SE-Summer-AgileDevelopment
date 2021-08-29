package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.QuestionnaireSubmitDao;
import com.example.SESummer.Entity.QuestionnaireSubmit;
import com.example.SESummer.Entity.TestScoreRecord;
import com.example.SESummer.Service.QuestionnaireSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireSubmitServiceImpl implements QuestionnaireSubmitService {
    @Autowired
    private QuestionnaireSubmitDao questionnaireSubmitDao;

    @Override
    //保存提交问卷记录
    public void addQuestionnaireSubmit(QuestionnaireSubmit questionnaireSubmit){
        questionnaireSubmitDao.addQuestionnaireSubmit(questionnaireSubmit);
    }

    @Override
    //用户是否提交过问卷
    public QuestionnaireSubmit haveFinish(Integer questionnaireID,Integer userID){
        return questionnaireSubmitDao.haveFinish(questionnaireID,userID);
    }

    @Override
    //用户是否填过问卷
    public QuestionnaireSubmit haveFill(Integer questionnaireID,Integer userID){
        return questionnaireSubmitDao.haveFill(questionnaireID,userID);
    }

    @Override
    //设置用户考试题得分
    public void setTestScoreRecord(TestScoreRecord testScoreRecord){
        questionnaireSubmitDao.setTestScoreRecord(testScoreRecord);
    }

    @Override
    //获取考试题平均分
    public Double getQuestionAverage(Integer questionContentID){
        return questionnaireSubmitDao.getQuestionAverage(questionContentID);
    }

    @Override
    //获取考试题总答题人数
    public Integer getQuestionTotal(Integer questionContentID){
        return questionnaireSubmitDao.getQuestionTotal(questionContentID);
    }

    @Override
    //获取考试题正确人数
    public Integer getRightCounts(Integer questionContentID){
        return questionnaireSubmitDao.getRightCounts(questionContentID);
    }
}