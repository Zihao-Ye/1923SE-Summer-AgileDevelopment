package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.UserChooseQuestionDao;
import com.example.SESummer.Entity.UserChooseQuestion;
import com.example.SESummer.Service.UserChooseQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserChooseQuestionServiceImpl implements UserChooseQuestionService {
    @Autowired
    private UserChooseQuestionDao userChooseQuestionDao;

    @Override
    //根据用户ID、问卷ID、题目ID删除已选选项记录
    public void delChooseRecord(Integer userID,Integer questionnaireID,Integer questionContentID){
        userChooseQuestionDao.delChooseRecord(userID,questionnaireID,questionContentID);
    }

    @Override
    //检索用户是否回答过该选择题
    public UserChooseQuestion getUserChooseRecordByID(Integer userID, Integer questionnaireID, Integer questionContentID){
        return userChooseQuestionDao.getUserChooseRecordByID(userID,questionnaireID,questionContentID);
    }

    @Override
    //添加用户选择题答题记录
    public void addChooseRecord(UserChooseQuestion userChooseQuestion){
        userChooseQuestionDao.addChooseRecord(userChooseQuestion);
    }

    @Override
    //获取一个选择题的所有选项填写记录
    public List<UserChooseQuestion> getUserChooseRecordOfQuestion(Integer userID, Integer questionnaireID, Integer questionContentID){
        return userChooseQuestionDao.getUserChooseRecordOfQuestion(userID,questionnaireID,questionContentID);
    }

    @Override
    //获取用户填写的所有选项
    public List<UserChooseQuestion> getAllChooseRecordOfQuestionnaireByUserIDAndQuestionnaireID(Integer userID,Integer questionnaireID){
        return userChooseQuestionDao.getAllChooseRecordOfQuestionnaireByUserIDAndQuestionnaireID(userID,questionnaireID);
    }

    @Override
    //删除用户填写该问卷的所有选项记录
    public void delRecord(Integer userID,Integer questionnaireID){
        userChooseQuestionDao.delRecord(userID,questionnaireID);
    }
}
