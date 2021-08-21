package com.example.SESummer.Service;

import com.example.SESummer.Entity.QuestionOption;
import com.example.SESummer.Entity.Questionnaire;
import com.example.SESummer.Entity.QuestionnaireContent;

import java.sql.Timestamp;

public interface QuestionnaireService {
    //创建问卷
    void createQuestionnaire(Questionnaire questionnaire);

    //添加问题
    void addQuestion(QuestionnaireContent questionnaireContent);

    //设置选项
    void setOptions(QuestionOption questionOption);
}
