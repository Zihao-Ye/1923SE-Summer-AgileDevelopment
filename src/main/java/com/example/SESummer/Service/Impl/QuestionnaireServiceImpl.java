package com.example.SESummer.Service.Impl;

import com.example.SESummer.Dao.QuestionnaireDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class QuestionnaireServiceImpl {
    @Autowired
    private QuestionnaireDao questionnaireDao;

    //创建新问卷
    public void createQuestionnaire(String Title, Timestamp CreateTime, Integer Kind, Integer RecycleVolume,String QuestionnairePwd,Integer UserID){
        questionnaireDao.createQuestionnaire(Title,CreateTime,Kind,RecycleVolume,QuestionnairePwd,UserID);
    }

    //添加新问题
    public void addQuestion(Integer QuestionnaireID,Integer QuestionKind,String QuestionContent,Integer QuestionOptionNumber,Integer RequireSig){
        questionnaireDao.addQuestion(QuestionnaireID,QuestionKind,QuestionContent,QuestionOptionNumber,RequireSig);
    }

    //添加新选项
    public void setOptions(Integer QuestionnaireID,Integer QuestionID,Integer VoteVolume,String OptionContent){
        questionnaireDao.setOptions(QuestionnaireID,QuestionID,OptionContent,VoteVolume);
    }
}
