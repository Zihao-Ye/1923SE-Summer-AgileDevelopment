package com.example.SESummer.Service;

import com.example.SESummer.Entity.*;

import java.util.List;

public interface DataOutputService {
    //根据问卷号获取问题信息
    public List<QuestionContent> getQuestionsByQuestionnaireID(Integer questionnaireID);

//    //根据问题序号获取选项信息
//    public List<QuestionOption> getQuestionOptionByID(Integer userID,Integer questionContentID);

    //根据用户ID和问题序号获取选择题填写情况
    UserChooseQuestion getChooseByID(Integer userID, Integer questionContentID);

    //根据用户ID和问题序号获取填空题填写情况
    UserCompletionQuestion getCompletionByID(Integer userID, Integer questionContentID);

    //根据用户ID和问题序号获取评分题填写情况
    UserScoreQuestion getScoreByID(Integer userID, Integer questionContentID);

    //根据用户ID获取用户名
    public String getUserNameByUserID(Integer userID);

    //根据提交情况获取所有填写该问卷的用户ID
    List<QuestionnaireSubmit> getUserIDByQuestionnaireID(Integer questionnaireID);

    //根据问卷ID获取问卷标题
    String getTitleByQuestionnaireID(Integer questionnaireID);

    //根据题目ID获取题干内容
    String getContentByQuestionID(Integer questionContentID);

    //根据题目ID获取所有选项
    List<QuestionOption> getAllOptionsByQuestionID(Integer questionContentID);

    //根据选项ID查找该选项是否被选
    UserChooseQuestion getOptionByOptionID(Integer userid,Integer questionContentID,Integer questionOptionID);

    //根据问卷号获取选择题题干
    List<QuestionContent> getChooseContentByID(Integer questionnaireID);

    //根据问卷号获取填空题题干
    List<QuestionContent> getCompletionContentByID(Integer questionnaireID);

    //根据问卷号获取评分题题干
    List<QuestionContent> getScoreContentByID(Integer questionnaireID);

    //根据题号获取问题实体
    QuestionContent getQuestionContentByID(Integer questionContentID);

    List<UserChooseQuestion> getChooseList(Integer questionContentID);

    List<UserCompletionQuestion> getCompletionList(Integer questionContentID);

    List<UserScoreQuestion> getScoreList(Integer questionContentID);
}
