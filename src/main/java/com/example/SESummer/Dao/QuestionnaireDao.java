package com.example.SESummer.Dao;

import com.example.SESummer.Entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface QuestionnaireDao {
    //创建问卷
    void createQuestionnaire(Questionnaire questionnaire);

    //修改问卷加密ID
    void editEncryptQuestionnaireID(Integer questionnaireID,String encryptQuestionnaireID);

    //获取用户最新创建的问卷
    Questionnaire getRecentQuestionnaireCreateByUserID(Integer userID);

    //添加问题
    void addQuestion(QuestionContent questionContent);

    //获取问卷下最新创建的问题
    QuestionContent getRecentQuestionByQuestionnaireID(Integer questionnaireID);

    //添加选择题选项
    void setOptions(QuestionOption questionOption);

    //添加评分题分数上限
    void setScore(ScoreQuestion scoreQuestion);

    //设置考试填空题答案
    void setCompletionAnswer(CompletionQuestion completionQuestion);

    //问题排序
    void rankQuestion(Integer questionContentID,Integer questionNo);

    //将问卷收入回收站
    void setRubbish(Integer questionnaireID);

    //将问卷移出回收站
    void recoverRubbish(Integer questionnaireID);

    //将回收站中的问卷删除
    void delRubbish(Integer questionnaireID);

    //根据用户ID获取所有不在回收站里的问卷
    List<Questionnaire> getQuestionnaireListNotRubbishByUserID(Integer userID);

    //根据用户ID获取所有在回收站里的问卷
    List<Questionnaire> getQuestionnaireListIsRubbishByUserID(Integer userID);

    //发布问卷
    void publishQuestionnaire(Integer questionnaireID);

    //开启问卷
    void openQuestionnaire(Integer questionnaireID);

    //关闭问卷
    void closeQuestionnaire(Integer questionnaireID, Timestamp endTime);

    //预览问卷-问卷信息
    Questionnaire getQuestionnaireByQuestionnaireID(Integer questionnaireID);

    //预览问卷-问卷题目信息
    List<QuestionContent> getAllQuestionContentOfQuestionnaireByQuestionnaireID(Integer questionnaireID);

    //预览问卷-题目选项信息
    List<QuestionOption> getAllQuestionOptionOfQuestionByQuestionContentID(Integer questionContentID);

    //预览问卷-评分题分数上限
    ScoreQuestion getScoreQuestionByQuestionContentID(Integer questionContentID);

    //预览问卷-考试填空题答案
    CompletionQuestion getCompletionQuestionByQuestionContentID(Integer questionContentID);

    //编辑问卷-修改问卷信息
    void editQuestionnaire(Integer questionnaireID,String title,Timestamp endTime,String questionnaireNote,String endMessage);

    //编辑问卷-修改问题信息
    void editQuestion(Integer questionContentID, Integer requireSig, String questionContent, String questionNote,Integer questionScore);

    //编辑问卷-修改选项信息
    void editOption(Integer questionOptionID,Integer optionKind,String optionContent,Integer leftVolume,Integer isAnswer,Integer optionNo);

    //编辑问卷-修改评分题信息
    void editScore(Integer scoreQuestionID, Integer maxScore,String startWord,String endWord);

    //编辑问卷-修改考试填空题答案
    void editCompletion(Integer completionQuestionID,String answer);

    //编辑问卷-删除题目的选项
    void delQuestionOptionRelatedToQuestion(Integer questionContentID);

    //编辑问卷-删除评分题的评分上限
    void delScoreQuestionRelatedToQuestion(Integer questionContentID);

    //编辑问卷-删除填空题的答案
    void delCompletionQuestionRelatedToQuestion(Integer questionContentID);

    //编辑问卷-删除题目
    void delQuestion(Integer questionContentID);

    //编辑问卷-删除选项
    void delQuestionOption(Integer questionOptionID);

    //复制问卷-复制问卷基本信息
    void copyQuestionnaire(Questionnaire questionnaire);

    //获取最近创建的问卷
    Questionnaire getRecentQuestionnaireByUserID(Integer masterID);

    //复制问卷-复制问题基本信息
    void copyQuestion(QuestionContent questionContent);
}
