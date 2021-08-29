package com.example.SESummer.Dao;

import com.example.SESummer.Entity.CompletionQuestion;
import com.example.SESummer.Entity.UserCompletionQuestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCompletionQuestionDao {
    //检索用户是否回答过该填空题
    UserCompletionQuestion getCompletionRecordByID(Integer userID, Integer questionnaireID, Integer questionContentID);

    //添加用户填空题答题记录
    void addCompletionRecord(Integer userID, Integer questionnaireID, Integer questionContentID, String completionContent);

    //修改用户填空题答题记录
    void updateCompletionRecord(Integer userID, Integer questionnaireID, Integer questionContentID, String questionContent);

    //判断填空题答案是否正确
    CompletionQuestion judgeCompletionScore(Integer questionContentID,String answer);

    //删除用户填写该问卷的所有填空记录
    void delRecord(Integer userID,Integer questionnaireID);
}
