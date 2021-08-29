package com.example.SESummer.Controller;

import com.example.SESummer.Entity.Questionnaire;
import com.example.SESummer.Service.QuestionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class ScheduleTask {
    @Autowired
    QuestionDataService questionDataService;

    @Scheduled(fixedRate = 60000)
    public void scheduledTask(){
        List<Questionnaire> questionnaires = questionDataService.getAllVisitableQuestionnaire();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        for (Questionnaire questionnaire:questionnaires){
            if (questionnaire.getEndTime() != null){
                if (questionnaire.getEndTime().before(time)){
                    questionDataService.updateVisitableQuestionnaire(questionnaire.getQuestionnaireID());
                    System.out.println("已关闭问卷:"+questionnaire.getQuestionnaireID());
                }
            }
        }
    }
}
