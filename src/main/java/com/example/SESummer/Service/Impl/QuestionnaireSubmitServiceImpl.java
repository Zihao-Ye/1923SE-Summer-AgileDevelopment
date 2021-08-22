package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.QuestionnaireSubmitDao;
import com.example.SESummer.Entity.QuestionnaireSubmit;
import com.example.SESummer.Service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireSubmitServiceImpl implements QuestionSubmitService {
    @Autowired
    private QuestionnaireSubmitDao questionnaireSubmitDao;

    @Override
    //保存提交问卷记录
    public void addQuestionSubmit(QuestionnaireSubmit submit){
        questionnaireSubmitDao.addQuestionSubmit(submit);
    }

    @Override
    //删除提交问卷记录
    public void deleteQuestionSubmit(QuestionnaireSubmit submit){
        questionnaireSubmitDao.deleteQuestionSubmit(submit);
    }
}
