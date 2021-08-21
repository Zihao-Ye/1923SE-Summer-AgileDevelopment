package com.example.SESummer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questionnaire_submit")
public class QuestionnaireSubmit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer submitID;
    private Integer userID;
    private Integer questionnaireID;
    private Integer isSubmit;
}
