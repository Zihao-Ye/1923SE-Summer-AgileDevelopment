package com.example.SESummer.Dao;

import com.example.SESummer.Entity.QuestionnaireSubmit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionnaireSubmitDao {
    //保存提交问卷记录
    void addQuestionSubmit(Integer userID,Integer questionnaireID,Integer isSubmit);

}
