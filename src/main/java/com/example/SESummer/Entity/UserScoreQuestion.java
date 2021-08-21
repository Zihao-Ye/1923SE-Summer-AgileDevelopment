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
    private Integer userScoreQuestionID;
    private Integer userID;
    private Integer questionnaireID;
    private Integer questionContentID;
    private Integer score;
}
