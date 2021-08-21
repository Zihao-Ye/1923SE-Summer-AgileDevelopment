package com.example.SESummer.Service;

import com.example.SESummer.Entity.UserCompletionQuestion;

public interface UserCompletionQuestionService {
    //检索改用户是否回答过该题
    UserCompletionQuestion getCompletionRecordByID(Integer userID,Integer questionnaireID,Integer questionContentID);

    //添加用户答题记录
    void addCompletionRecord(UserCompletionQuestion completion);

    //修改用户答题记录
    void updateCompletionRecord(UserCompletionQuestion completion);
}
