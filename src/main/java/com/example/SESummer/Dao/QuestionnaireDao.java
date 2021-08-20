package com.example.SESummer.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;

@Mapper
public interface QuestionnaireDao {
    //创建问卷
    void createQuestionnaire(String Title, Timestamp CreateTime, Integer Kind, Integer RecycleVolume,String QuestionnairePwd,Integer UserID);

    //添加问题
    void addQuestion(Integer QuestionnaireID,Integer QuestionKind,String QuestionContent,Integer QuestionOptionNumber,Integer RequireSig);

    //设置选项
    void setOptions(Integer QuestionnaireID,Integer QuestionID,String OptionContent,Integer VoteVolume);

}
