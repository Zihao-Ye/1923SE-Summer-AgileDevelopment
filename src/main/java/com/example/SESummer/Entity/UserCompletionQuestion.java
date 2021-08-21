package com.example.SESummer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_completion_question")
public class UserCompletionQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userCompletionQuestionID;
    private Integer userID;
    private Integer questionnaireID;
    private Integer questionContentID;
    private String completionContent;
}
