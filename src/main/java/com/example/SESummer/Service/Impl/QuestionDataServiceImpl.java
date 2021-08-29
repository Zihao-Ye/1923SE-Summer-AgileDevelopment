package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.QuestionDataDao;
import com.example.SESummer.Entity.QuestionContent;
import com.example.SESummer.Entity.QuestionOption;
import com.example.SESummer.Entity.Questionnaire;
import com.example.SESummer.Service.QuestionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionDataServiceImpl implements QuestionDataService {
    @Autowired
    private QuestionDataDao questionDataDao;

    @Override
    //提交后增加问卷回收数
    public void addRecycleVolume(Integer questionnaireID){
        questionDataDao.addRecycleVolume(questionnaireID);
    }

    @Override
    //增加选项被选次数
    public void addVoteVolume(Integer questionOptionID){
        questionDataDao.addVoteVolume(questionOptionID);
    }

    @Override
    //根据问题ID、用户ID和问卷ID，查询填过的选项，取消填选选项（选项总数-1）
    public void minusVoteVolume(Integer questionnaireID,Integer userID,Integer questionContentID){
        questionDataDao.minusVoteVolume(questionnaireID,userID,questionContentID);
    }

    @Override
    //更新评分题平均分
    public void updateAverageScore(Integer questionContentID){
        questionDataDao.updateAverageScore(questionContentID);
    }

    @Override
    public QuestionContent getQuestionContent(Integer questionContent){
        return questionDataDao.getQuestionContent(questionContent);
    }

    @Override
    //获取所有已公布的问卷
    public List<Questionnaire> getAllVisitableQuestionnaire(){
        return questionDataDao.getAllVisitableQuestionnaire();
    }

    @Override
    //修改问卷发布情况
    public void updateVisitableQuestionnaire(Integer questionnaireID){
        questionDataDao.updateVisitableQuestionnaire(questionnaireID);
    }

    @Override
    //根据选项号获取选项
    public QuestionOption getQuestionOption(Integer questionOptionID){
        return questionDataDao.getQuestionOption(questionOptionID);
    }
}
