package com.example.SESummer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_score_question")
public class UserScoreQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userScoreQuestionID;//评分题提交记录
    private Integer userID;//用户ID
    private Integer questionnaireID;//问卷ID
    private Integer questionContentID;//问题ID
    private Integer score;//评分题选择的分数
}
