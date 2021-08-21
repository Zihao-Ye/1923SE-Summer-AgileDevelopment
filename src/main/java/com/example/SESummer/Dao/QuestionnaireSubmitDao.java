package com.example.SESummer.Dao;

import com.example.SESummer.Entity.QuestionnaireSubmit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionnaireSubmitDao {
    //提交信息保存
    void addQuestionSubmit(QuestionnaireSubmit submit);

    //删除信息
    void deleteQuestionSubmit(QuestionnaireSubmit submit);
}
