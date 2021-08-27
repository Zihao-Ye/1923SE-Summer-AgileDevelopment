package com.example.SESummer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questionnaire_submit")
public class QuestionnaireSubmit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer submitID;//提交记录ID
    private Integer userID;//用户ID
    private Integer questionnaireID;//问卷ID
    private Integer isSubmit;//是否已提交
    private Timestamp submitTime;//提交时间
}
