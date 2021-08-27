package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.QuestionnaireSubmitDao;
import com.example.SESummer.Entity.QuestionnaireSubmit;
import com.example.SESummer.Service.QuestionnaireSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireSubmitServiceImpl implements QuestionnaireSubmitService {
    @Autowired
    private QuestionnaireSubmitDao questionnaireSubmitDao;

    @Override
    //保存提交问卷记录
    public void addQuestionnaireSubmit(QuestionnaireSubmit questionnaireSubmit){
        questionnaireSubmitDao.addQuestionnaireSubmit(questionnaireSubmit);
    }

}
