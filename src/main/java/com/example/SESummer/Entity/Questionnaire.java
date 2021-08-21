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
@Table(name = "questionnaire")
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionnaireID;
    private String title;
    private Timestamp createTime;
    private Integer recycleVolume;
    private Integer kind;
    private String questionPwd;
    private Integer isPrivate;
    private Integer isVisitable;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer masterID;
    private Integer isRubbish;
}
