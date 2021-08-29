package com.example.SESummer.Service;

import com.example.SESummer.Entity.QuestionnaireSubmit;
import com.example.SESummer.Entity.TestScoreRecord;

public interface QuestionnaireSubmitService {
    //提交信息保存
    void addQuestionnaireSubmit(QuestionnaireSubmit questionnaireSubmit);

    //用户是否提交过问卷
    QuestionnaireSubmit haveFinish(Integer questionnaireID,Integer userID);

    //用户是否填过问卷
    QuestionnaireSubmit haveFill(Integer questionnaireID,Integer userID);

    //设置用户考试题得分
    void setTestScoreRecord(TestScoreRecord testScoreRecord);
}
