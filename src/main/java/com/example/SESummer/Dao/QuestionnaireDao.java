package com.example.SESummer.Dao;

import com.example.SESummer.Entity.QuestionOption;
import com.example.SESummer.Entity.Questionnaire;
import com.example.SESummer.Entity.QuestionnaireContent;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;

@Mapper
public interface QuestionnaireDao {
    //创建问卷
    void createQuestionnaire(Questionnaire questionnaire);

    //添加问题
    void addQuestion(QuestionnaireContent questionnaireContent);

    //设置选项
    void setOptions(QuestionOption questionOption);

}