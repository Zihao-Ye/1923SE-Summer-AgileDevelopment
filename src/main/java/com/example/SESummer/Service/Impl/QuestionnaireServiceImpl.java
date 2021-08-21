package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.QuestionnaireDao;
import com.example.SESummer.Entity.QuestionOption;
import com.example.SESummer.Entity.Questionnaire;
import com.example.SESummer.Entity.QuestionnaireContent;
import com.example.SESummer.Service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    private QuestionnaireDao questionnaireDao;

    @Override
    //创建新问卷
    public void createQuestionnaire(Questionnaire questionnaire){
        questionnaireDao.createQuestionnaire(questionnaire);
    }

    @Override
    //添加新问题
    public void addQuestion(QuestionnaireContent questionnaireContent){
        questionnaireDao.addQuestion(questionnaireContent);
    }

    @Override
    //添加新选项
    public void setOptions(QuestionOption questionOption){
        questionnaireDao.setOptions(questionOption);
    }
}
