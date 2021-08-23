package com.example.SESummer.Service;

public interface QuestionDataService {
    //提交后增加问卷回收数
    void addRecycleVolume(Integer questionnaireID);

    //增加选项被选次数
    void addVoteVolume(Integer questionOptionID);

    //根据问题ID、用户ID和问卷ID，查询填过的选项，取消填选选项（选项总数-1）
    void minusVoteVolume(Integer questionnaireID,Integer userID,Integer questionContentID);

    //更新评分题平均分
    void updateAverageScore(Integer questionContentID);
}
