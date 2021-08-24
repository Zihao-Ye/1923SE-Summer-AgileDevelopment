package com.example.SESummer.Service;

import com.example.SESummer.Entity.QuestionnaireSubmit;

public interface QuestionSubmitService {
    //提交信息保存
    void addQuestionSubmit(Integer userID,Integer questionnaireID,Integer isSubmit);


}
