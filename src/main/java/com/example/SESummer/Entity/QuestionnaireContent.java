package com.example.SESummer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questionnaire_content")
public class QuestionnaireContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionnaireContentID;
    private Integer questionnaireID;
    private Integer questionKind;
    private Integer requireSig;
    private String questionContent;
    private Integer questionOptionNumber;
}
