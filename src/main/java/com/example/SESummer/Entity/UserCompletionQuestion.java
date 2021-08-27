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
    private Integer userCompletionQuestionID;//填空题提交记录ID
    private Integer userID;//用户ID
    private Integer questionnaireID;//问卷ID
    private Integer questionContentID;//问题ID
    private String completionContent;//填空内容
}
