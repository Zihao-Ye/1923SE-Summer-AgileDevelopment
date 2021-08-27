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
    private Integer userChooseQuestionID;//选择选项记录ID
    private Integer userID;//用户ID
    private Integer questionnaireID;//填写的问卷ID
    private Integer questionContentID;//填写的题目ID
    private Integer questionOptionID;//选择的选项ID
}
