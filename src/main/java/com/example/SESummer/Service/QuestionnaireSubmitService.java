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

    //获取考试题平均分
    Double getQuestionAverage(Integer questionContentID);

    //获取考试题总答题人数
    Integer getQuestionTotal(Integer questionContentID);

    //获取考试题正确人数
    Integer getRightCounts(Integer questionContentID);
}