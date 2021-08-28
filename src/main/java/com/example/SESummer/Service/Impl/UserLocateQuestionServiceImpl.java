package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.UserLocateQuestionDao;
import com.example.SESummer.Entity.UserLocateQuestion;
import com.example.SESummer.Service.UserLocateQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLocateQuestionServiceImpl implements UserLocateQuestionService {
    @Autowired
    private UserLocateQuestionDao userLocateQuestionDao;

    //检索用户是否答过该定位题
    public UserLocateQuestion getLocateRecordByID(Integer userID, Integer questionnaireID, Integer questionContentID){
        return userLocateQuestionDao.getLocateRecordByID(userID,questionnaireID,questionContentID);
    }

    @Override
    //添加用户定位记录
    public void addLocateRecord(UserLocateQuestion userLocateQuestion){
        userLocateQuestionDao.addLocateRecord(userLocateQuestion);
    }

    @Override
    //修改用户定位记录
    public void updateLocateRecord(Integer userID,Integer questionnaireID,Integer questionContentID,String locate){
        userLocateQuestionDao.updateLocateRecord(userID,questionnaireID,questionContentID,locate);
    }
}
