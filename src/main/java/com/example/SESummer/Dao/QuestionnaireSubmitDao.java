package com.example.SESummer.Dao;

import com.example.SESummer.Entity.QuestionnaireSubmit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionnaireSubmitDao {
    //保存提交问卷记录
    void addQuestionnaireSubmit(QuestionnaireSubmit questionnaireSubmit);

    //用户是否提交过问卷
    QuestionnaireSubmit haveFinish(Integer questionnaireID,Integer userID);

    //用户是否填过问卷
    QuestionnaireSubmit haveFill(Integer questionnaireID,Integer userID);
}
