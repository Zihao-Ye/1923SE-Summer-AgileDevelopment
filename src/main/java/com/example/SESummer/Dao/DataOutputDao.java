package com.example.SESummer.Dao;

import com.example.SESummer.Entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataOutputDao {
    //根据问卷号获取问题信息
    public List<QuestionContent> getQuestionsByQuestionnaireID(Integer userID,Integer questionnaireID);

//    //根据问题序号获取选项信息
//    public List<QuestionOption> getQuestionOptionByQuestionContentID(Integer userID,Integer questionContentID);

    //根据问题序号获取选择题填写情况
    public List<UserChooseQuestion> getChooseByID(Integer userID,Integer questionnaireID);

    //根据问题序号获取填空题填写情况
    public List<UserCompletionQuestion> getCompletionByID(Integer userID,Integer questionnaireID);

    //根据问题序号获取评分题填写情况
    public List<UserScoreQuestion> getScoreByID(Integer userID,Integer questionnaireID);

    //根据用户ID获取用户名
    public String getUserNameByUserID(Integer userID);

    //根据提交情况获取所有填写该问卷的用户ID
    List<QuestionnaireSubmit> getUserIDByQuestionnaireID(Integer questionnaireID);

    //根据问卷ID获取标题
    String getTitleByQuestionnaireID(Integer questionnaireID);

    //根据题目ID获取题干内容
    String getContentByQuestionID(Integer questionContentID);

    //根据题目ID获取所有选项ID
    List<QuestionOption> getAllOptionsByQuestionID(Integer questionContentID);

    //根据选项ID查找该选项是否被选
    List<UserChooseQuestion> getOptionByOptionID(Integer userid,Integer questionOptionID);
}
