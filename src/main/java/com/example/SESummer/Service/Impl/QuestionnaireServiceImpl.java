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

    @Override
    //预览问卷-问卷信息
    public Questionnaire getQuestionnaireByQuestionnaireID(Integer questionnaireID){
        return questionnaireDao.getQuestionnaireByQuestionnaireID(questionnaireID);
    }

    @Override
    //预览问卷-问卷题目信息
    public List<QuestionContent> getAllQuestionContentOfQuestionnaireByQuestionnaireID(Integer questionnaireID){
        return questionnaireDao.getAllQuestionContentOfQuestionnaireByQuestionnaireID(questionnaireID);
    }

    @Override
    //预览问卷-题目选项信息
    public List<QuestionOption> getAllQuestionOptionOfQuestionByQuestionContentID(Integer questionContentID){
        return questionnaireDao.getAllQuestionOptionOfQuestionByQuestionContentID(questionContentID);
    }

    @Override
    //预览问卷-评分题分数上限
    public ScoreQuestion getScoreQuestionByQuestionContentID(Integer questionContentID){
        return questionnaireDao.getScoreQuestionByQuestionContentID(questionContentID);
    }

    @Override
    //编辑问卷-修改问卷信息
    public void editQuestionnaire(Integer questionnaireID,String title,String questionPwd,Integer isPrivate){
        questionnaireDao.editQuestionnaire(questionnaireID,title,questionPwd,isPrivate);
    }

    @Override
    //编辑问卷-修改问题信息
    public void editQuestion(Integer questionContentID,Integer requireSig,String questionContent){
        questionnaireDao.editQuestion(questionContentID,requireSig,questionContent);
    }
}
