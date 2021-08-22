package com.example.SESummer.Service;

import com.example.SESummer.Entity.QuestionContent;
import com.example.SESummer.Entity.QuestionOption;
import com.example.SESummer.Entity.Questionnaire;
import com.example.SESummer.Entity.ScoreQuestion;

import java.sql.Timestamp;
import java.util.List;

public interface QuestionnaireService {
    //创建问卷
    void createQuestionnaire(Questionnaire questionnaire);

    //添加问题
    void addQuestion(QuestionContent questionContent);

    //添加选择题选项
    void setOptions(QuestionOption questionOption);

    //添加评分题分数上限
    void setScore(ScoreQuestion scoreQuestion);

    //将问卷收入回收站
    void setRubbish(Integer questionnaireID);

    //将问卷移出回收站
    void recoverRubbish(Integer questionnaireID);

    //根据用户ID获取所有不在回收站里的问卷
    List<Questionnaire> getQuestionnaireListNotRubbishByUserID(Integer userID);

    //根据用户ID获取所有在回收站里的问卷
    List<Questionnaire> getQuestionnaireListIsRubbishByUserID(Integer userID);

    //发布问卷
    void publishQuestionnaire(Integer questionnaireID, Timestamp startTime);

    //开启问卷
    void openQuestionnaire(Integer questionnaireID);

    //关闭问卷
    void closeQuestionnaire(Integer questionnaireID);

    //预览问卷-问卷信息
    Questionnaire getQuestionnaireByQuestionnaireID(Integer questionnaireID);

    //预览问卷-问卷题目信息
    List<QuestionContent> getAllQuestionContentOfQuestionnaireByQuestionnaireID(Integer questionnaireID);

    //预览问卷-题目选项信息
    List<QuestionOption> getAllQuestionOptionOfQuestionByQuestionContentID(Integer questionContentID);

    //预览问卷-评分题分数上限
    ScoreQuestion getScoreQuestionByQuestionContentID(Integer questionContentID);

    //编辑问卷-修改问卷信息
    void editQuestionnaire(Integer questionnaireID, String title, String questionPwd, Integer isPrivate, String questionnaireNote);

    //编辑问卷-修改问题信息
    void editQuestion(Integer questionContentID, Integer requireSig, String questionContent, String questionNote);
}
