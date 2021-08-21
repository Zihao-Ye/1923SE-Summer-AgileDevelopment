package com.example.SESummer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_content")
public class QuestionContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionContentID;
    private Integer questionnaireID;
    private Integer questionKind;
    private Integer requireSig;
    private String questionContent;
    private Integer questionNo;
}
