package com.example.SESummer.Service;

import com.example.SESummer.Entity.UserCompletionQuestion;

public interface UserCompletionQuestionService {
    //检索用户是否回答过该填空题
    UserCompletionQuestion getCompletionRecordByID(Integer userID, Integer questionnaireID, Integer questionContentID);

    //添加用户填空题答题记录
    void addCompletionRecord(UserCompletionQuestion completion);

    //修改用户填空题答题记录
    void updateCompletionRecord(Integer userID, Integer questionnaireID, Integer questionContentID,String questionContent);
}
