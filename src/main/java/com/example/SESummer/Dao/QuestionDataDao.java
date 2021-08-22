package com.example.SESummer.Dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionDataDao {
    //提交后增加问卷回收数
    public void addRecycleVolume(Integer questionnaireID);

    //增加选项被选次数
    public void addVoteVolume(Integer questionOptionID);

    //减少选项的剩余次数
    public void reduceLeftVolume(Integer questionOptionID);
}
