package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.QuestionDataDao;
import com.example.SESummer.Service.QuestionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDataServiceImpl implements QuestionDataService {
    @Autowired
    private QuestionDataDao questionDataDao;

    @Override
    public void addRecycleVolume(Integer questionnaireID){
        questionDataDao.addRecycleVolume(questionnaireID);
    }

    @Override
    public void addVoteVolume(Integer questionOptionID){
        questionDataDao.addVoteVolume(questionOptionID);
    }

    @Override
    public void reduceLeftVolume(Integer questionOptionID){
        questionDataDao.reduceLeftVolume(questionOptionID);
    }
}
