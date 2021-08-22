package com.example.SESummer.Service;

import com.example.SESummer.Entity.UserChooseQuestion;

public interface UserChooseQuestionService {
    //根据用户ID、问卷ID、题目ID删除已选选项记录
    void delChooseRecord(Integer userID,Integer questionnaireID,Integer questionContentID);

    //检索用户是否回答过该选择题
    UserChooseQuestion getUserChooseRecordByID(Integer userID, Integer questionnaireID, Integer questionContentID);

    //添加用户选择题答题记录
    void addChooseRecord(UserChooseQuestion userChooseQuestion);
}
