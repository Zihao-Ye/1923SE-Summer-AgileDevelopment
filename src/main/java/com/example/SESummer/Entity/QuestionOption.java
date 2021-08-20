package com.example.SESummer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_option")
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionOptionID;
    private Integer questionnaireID;
    private Integer questionnaireContentID;
    private String optionContent;
    private Integer voteVolume;
}
