package com.example.SESummer.Dao;

import com.example.SESummer.Entity.UserChooseQuestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserChooseQuestionDao {
    //根据用户ID、问卷ID、题目ID删除已选选项记录
    void delChooseRecord(Integer userID,Integer questionnaireID,Integer questionContentID);

    //检索用户是否回答过该选择题
    UserChooseQuestion getUserChooseRecordByID(Integer userID,Integer questionnaireID,Integer questionContentID);

    //添加用户选择题答题记录
    void addChooseRecord(UserChooseQuestion userChooseQuestion);
}
