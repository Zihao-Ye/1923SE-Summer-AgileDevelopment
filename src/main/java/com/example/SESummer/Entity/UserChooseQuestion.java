package com.example.SESummer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_choose_question")
public class UserChooseQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userChooseQuestionID;
    private Integer userID;
    private Integer questionnaireID;
    private Integer questionContentID;
    private Integer questionOptionID;
}
