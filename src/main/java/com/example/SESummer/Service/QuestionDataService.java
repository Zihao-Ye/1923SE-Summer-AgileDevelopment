package com.example.SESummer.Service;

import com.example.SESummer.Entity.QuestionContent;
import com.example.SESummer.Entity.Questionnaire;

import java.util.List;

public interface QuestionDataService {
    //提交后增加问卷回收数
    void addRecycleVolume(Integer questionnaireID);

    //增加选项被选次数
    void addVoteVolume(Integer questionOptionID);

    //根据问题ID、用户ID和问卷ID，查询填过的选项，取消填选选项（选项总数-1）
    void minusVoteVolume(Integer questionnaireID,Integer userID,Integer questionContentID);

    //更新评分题平均分
    void updateAverageScore(Integer questionContentID);

    QuestionContent getQuestionContent(Integer questionContentID);

    //获取所有已公布的问卷
    List<Questionnaire> getAllVisitableQuestionnaire();

    //修改问卷发布情况
    void updateVisitableQuestionnaire(Integer questionnaireID);
}
