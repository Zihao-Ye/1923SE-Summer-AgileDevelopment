package com.example.SESummer.Dao;

import com.example.SESummer.Entity.UserChooseQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserChooseQuestionDao {
    //根据用户ID、问卷ID、题目ID删除已选选项记录
    void delChooseRecord(Integer userID,Integer questionnaireID,Integer questionContentID);

    //检索用户是否回答过该选择题
    UserChooseQuestion getUserChooseRecordByID(Integer userID,Integer questionnaireID,Integer questionContentID);

    //添加用户选择题答题记录
    void addChooseRecord(UserChooseQuestion userChooseQuestion);

    //获取一个选择题的所有选项填写记录
    List<UserChooseQuestion> getUserChooseRecordOfQuestion(Integer userID, Integer questionnaireID, Integer questionContentID);

    //获取用户填写的所有选项
    List<UserChooseQuestion> getAllChooseRecordOfQuestionnaireByUserIDAndQuestionnaireID(Integer userID,Integer questionnaireID);

    //删除用户填写该问卷的所有选项记录
    void delRecord(Integer userID,Integer questionnaireID);
}
