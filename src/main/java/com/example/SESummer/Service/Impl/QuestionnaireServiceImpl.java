package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.QuestionnaireDao;
import com.example.SESummer.Entity.QuestionContent;
import com.example.SESummer.Entity.QuestionOption;
import com.example.SESummer.Entity.Questionnaire;
import com.example.SESummer.Entity.ScoreQuestion;
import com.example.SESummer.Service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    //问题排序
    public void rankQuestion(Integer questionContentID,Integer questionNo){
        questionnaireDao.rankQuestion(questionContentID,questionNo);
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
    //发布问卷
    public void publishQuestionnaire(Integer questionnaireID, Timestamp startTime){
        questionnaireDao.publishQuestionnaire(questionnaireID,startTime);
    }

    @Override
    //开启问卷
    public void openQuestionnaire(Integer questionnaireID){
        questionnaireDao.openQuestionnaire(questionnaireID);
    }

    @Override
    //关闭问卷
    public void closeQuestionnaire(Integer questionnaireID, Timestamp endTime){
        questionnaireDao.closeQuestionnaire(questionnaireID,endTime);
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
    public void editQuestionnaire(Integer questionnaireID, String title, String questionPwd, Integer isPrivate, String questionnaireNote){
        questionnaireDao.editQuestionnaire(questionnaireID,title,questionPwd,isPrivate,questionnaireNote);
    }

    @Override
    //编辑问卷-修改问题信息
    public void editQuestion(Integer questionContentID, Integer requireSig, String questionContent, String questionNote){
        questionnaireDao.editQuestion(questionContentID,requireSig,questionContent,questionNote);
    }

    @Override
    //编辑问卷-删除题目的选项
    public void delQuestionOptionRelatedToQuestion(Integer questionContentID){
        questionnaireDao.delQuestionOptionRelatedToQuestion(questionContentID);
    }

    @Override
    //编辑问卷-删除题目
    public void delQuestion(Integer questionContentID){
        questionnaireDao.delQuestion(questionContentID);
    }

    @Override
    //编辑问卷-删除选项
    public void delQuestionOption(Integer questionOptionID){
        questionnaireDao.delQuestionOption(questionOptionID);
    }

    @Override
    //复制问卷-复制问卷基本信息
    public void copyQuestionnaire(Questionnaire questionnaire){
        questionnaireDao.copyQuestionnaire(questionnaire);
    }

    @Override
    //获取最近创建的问卷
    public Questionnaire getRecentQuestionnaireByUserID(Integer masterID){
        return questionnaireDao.getRecentQuestionnaireByUserID(masterID);
    }

    @Override
    //复制问卷-复制问题基本信息
    public void copyQuestion(QuestionContent questionContent){
        questionnaireDao.copyQuestion(questionContent);
    }

    @Override
    //获取最近创建的问题
    public QuestionContent getRecentQuestionByQuestionnaireID(Integer questionnaireID){
        return questionnaireDao.getRecentQuestionByQuestionnaireID(questionnaireID);
    }
}
