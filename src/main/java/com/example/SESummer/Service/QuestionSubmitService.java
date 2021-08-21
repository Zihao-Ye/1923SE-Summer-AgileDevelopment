package com.example.SESummer.Service;

import com.example.SESummer.Entity.QuestionnaireSubmit;

public interface QuestionSubmitService {
    //提交信息保存
    void addQuestionSubmit(QuestionnaireSubmit submit);

    //删除信息
    void deleteQuestionSubmit(QuestionnaireSubmit submit);

}
