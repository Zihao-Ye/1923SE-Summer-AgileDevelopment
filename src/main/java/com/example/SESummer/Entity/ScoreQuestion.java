package com.example.SESummer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "score_question")
public class ScoreQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scoreQuestionID;
    private Integer questionContentID;
    private Integer maxScore;
}
