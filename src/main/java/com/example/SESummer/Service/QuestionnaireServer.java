package com.example.SESummer.Service;

import java.sql.Timestamp;

public interface QuestionnaireServer {
    //添加问卷基础信息
    void createQuestionnaire(String Title, Timestamp CreateTime,Integer Kind,Integer RecycleVolume,String QuestionnairePwd,Integer UserID);

    //添加问题
    void addQuestion(Integer QuestionnaireID,Integer QuestionKind,String QuestionContent,Integer QuestionOptionNumber,Integer RequireSig);

    //设置选项
    void setOptions(Integer QuestionnaireID,Integer QuestionID,String OptionContent,Integer VoteVolume);
}
