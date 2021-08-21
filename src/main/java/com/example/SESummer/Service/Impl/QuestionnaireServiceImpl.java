package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.QuestionnaireDao;
import com.example.SESummer.Entity.QuestionContent;
import com.example.SESummer.Entity.QuestionOption;
import com.example.SESummer.Entity.Questionnaire;
import com.example.SESummer.Entity.ScoreQuestion;
import com.example.SESummer.Service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    private QuestionnaireDao questionnaireDao;

    @Override
    //创建问卷
    public void createQuestionnaire(Questionnaire questionnaire){
        questionnaireDao.createQuestionnaire(questionnaire);
    }

    @Override
    //添加问题
    public void addQuestion(QuestionContent questionContent){
        questionnaireDao.addQuestion(questionContent);
    }

    @Override
    //添加选择题选项
    public void setOptions(QuestionOption questionOption){
        questionnaireDao.setOptions(questionOption);
    }

    @Override
    //添加评分题分数上限
    public void setScore(ScoreQuestion scoreQuestion){
        questionnaireDao.setScore(scoreQuestion);
    }

    @Override
    //将问卷收入回收站
    public void setRubbish(Integer questionnaireID){
        questionnaireDao.setRubbish(questionnaireID);
    }

    @Override
    //将问卷移出回收站
    public void recoverRubbish(Integer questionnaireID){
        questionnaireDao.recoverRubbish(questionnaireID);
    }

    @Override
    //根据用户ID获取所有不在回收站里的问卷
    public List<Questionnaire> getQuestionnaireListNotRubbishByUserID(Integer userID){
        return questionnaireDao.getQuestionnaireListNotRubbishByUserID(userID);
    }

    @Override
    //根据用户ID获取所有在回收站里的问卷
    public List<Questionnaire> getQuestionnaireListIsRubbishByUserID(Integer userID){
        return questionnaireDao.getQuestionnaireListIsRubbishByUserID(userID);
    }

    @Override
    //开启问卷
    public void openQuestionnaire(Integer questionnaireID){
        questionnaireDao.openQuestionnaire(questionnaireID);
    }

    @Override
    //关闭问卷
    public void closeQuestionnaire(Integer questionnaireID){
        questionnaireDao.closeQuestionnaire(questionnaireID);
    }
}
