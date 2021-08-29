package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.QuestionnaireDao;
import com.example.SESummer.Entity.*;
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
    //修改问卷加密ID
    public void editEncryptQuestionnaireID(Integer questionnaireID,String encryptQuestionnaireID){
        questionnaireDao.editEncryptQuestionnaireID(questionnaireID,encryptQuestionnaireID);
    }

    @Override
    //获取用户最新创建的问卷
    public Questionnaire getRecentQuestionnaireCreateByUserID(Integer userID){
        return questionnaireDao.getRecentQuestionnaireCreateByUserID(userID);
    }

    @Override
    //添加问题
    public void addQuestion(QuestionContent questionContent){
        questionnaireDao.addQuestion(questionContent);
    }

    @Override
    //获取问卷下最新创建的问题
    public QuestionContent getRecentQuestionByQuestionnaireID(Integer questionnaireID){
        return questionnaireDao.getRecentQuestionByQuestionnaireID(questionnaireID);
    }

    @Override
    //添加选择题选项
    public void setOptions(QuestionOption questionOption){
        questionnaireDao.setOptions(questionOption);
    }

    @Override
    //根据问题ID于选项序号获取选项
    public QuestionOption getRecentQuestionOptionByQuestionContentIDAndOptionNo(Integer questionContentID,Integer optionNo){
        return questionnaireDao.getRecentQuestionOptionByQuestionContentIDAndOptionNo(questionContentID,optionNo);
    }

    @Override
    //添加评分题分数上限
    public void setScore(ScoreQuestion scoreQuestion){
        questionnaireDao.setScore(scoreQuestion);
    }

    @Override
    //获取问题的评分题信息
    public ScoreQuestion getRecentScoreQuestionByQuestionContentID(Integer questionContentID){
        return questionnaireDao.getRecentScoreQuestionByQuestionContentID(questionContentID);
    }

    @Override
    //设置考试填空题答案
    public void setCompletionAnswer(CompletionQuestion completionQuestion){
        questionnaireDao.setCompletionAnswer(completionQuestion);
    }

    @Override
    //获取考试填空题的答案
    public CompletionQuestion getRecentCompletionQuestionByQuestionContentID(Integer questionContentID){
        return questionnaireDao.getRecentCompletionQuestionByQuestionContentID(questionContentID);
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
    //将回收站中的问卷删除
    public void delRubbish(Integer questionnaireID){
        questionnaireDao.delRubbish(questionnaireID);
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
    public void publishQuestionnaire(Integer questionnaireID){
        questionnaireDao.publishQuestionnaire(questionnaireID);
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
    //预览问卷-考试填空题答案
    public CompletionQuestion getCompletionQuestionByQuestionContentID(Integer questionContentID){
        return questionnaireDao.getCompletionQuestionByQuestionContentID(questionContentID);
    }

    @Override
    //编辑问卷-修改问卷信息
    public void editQuestionnaire(Integer questionnaireID,String title,Timestamp endTime,String questionnaireNote,String endMessage){
        questionnaireDao.editQuestionnaire(questionnaireID,title,endTime,questionnaireNote,endMessage);
    }

    @Override
    //编辑问卷-修改问题信息
    public void editQuestion(Integer questionContentID, Integer requireSig, String questionContent, String questionNote,Integer questionScore){
        questionnaireDao.editQuestion(questionContentID,requireSig,questionContent,questionNote,questionScore);
    }

    @Override
    //编辑问卷-修改选项信息
    public void editOption(Integer questionOptionID,Integer optionKind,String optionContent,Integer leftVolume,Integer isAnswer,Integer optionNo){
        questionnaireDao.editOption(questionOptionID,optionKind,optionContent,leftVolume,isAnswer,optionNo);
    }

    @Override
    //编辑问卷-修改评分题信息
    public void editScore(Integer scoreQuestionID, Integer maxScore,String startWord,String endWord){
        questionnaireDao.editScore(scoreQuestionID,maxScore,startWord,endWord);
    }

    @Override
    //编辑问卷-修改考试填空题答案
    public void editCompletion(Integer completionQuestionID,String answer){
        questionnaireDao.editCompletion(completionQuestionID,answer);
    }

    @Override
    //编辑问卷-删除题目的选项
    public void delQuestionOptionRelatedToQuestion(Integer questionContentID){
        questionnaireDao.delQuestionOptionRelatedToQuestion(questionContentID);
    }

    @Override
    //编辑问卷-删除评分题的评分上限
    public void delScoreQuestionRelatedToQuestion(Integer questionContentID){
        questionnaireDao.delScoreQuestionRelatedToQuestion(questionContentID);
    }

    @Override
    //编辑问卷-删除填空题的答案
    public void delCompletionQuestionRelatedToQuestion(Integer questionContentID){
        questionnaireDao.delCompletionQuestionRelatedToQuestion(questionContentID);
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
    //根据加密ID获取原ID
    public Integer getOriginQuestionnaireID(String encryptQuestionnaireID){
        return questionnaireDao.getOriginQuestionnaireID(encryptQuestionnaireID);
    }

    @Override
    //添加默认问题
    public void addDefaultQuestion(QuestionContent questionContent){
        questionnaireDao.addDefaultQuestion(questionContent);
    }

    @Override
    //添加默认选项
    public void addDefaultOption(QuestionOption questionOption){
        questionnaireDao.addDefaultOption(questionOption);
    }

    @Override
    //根据问题ID获取问题
    public QuestionContent getQuestionByQuestionContentID(Integer questionContentID){
        return questionnaireDao.getQuestionByQuestionContentID(questionContentID);
    }

    @Override
    //获取选择的所有正确选项
    public List<QuestionOption> getAllAnswerOptionOfQuestion(Integer questionContentID){
        return questionnaireDao.getAllAnswerOptionOfQuestion(questionContentID);
    }

    @Override
    //根据选项ID获取选项
    public QuestionOption getQuestionOptionByQuestionOptionID(Integer questionOptionID){
        return questionnaireDao.getQuestionOptionByQuestionOptionID(questionOptionID);
    }

    @Override
    //设置乱序
    public void setTestQuestionRank(TestQuestionRank testQuestionRank){
        questionnaireDao.setTestQuestionRank(testQuestionRank);
    }

    @Override
    //获取用户对某张考试问卷的题目顺序
    public List<TestQuestionRank> getUserTestQuestionRank(Integer questionnaireID,Integer userID){
        return questionnaireDao.getUserTestQuestionRank(questionnaireID,userID);
    }
}
