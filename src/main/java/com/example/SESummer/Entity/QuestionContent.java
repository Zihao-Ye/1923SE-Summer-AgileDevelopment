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
    private Integer questionContentID;//问题ID
    private Integer questionnaireID;//问题所属问卷ID
    private Integer questionKind;//问题种类：1-单选，2-多选，3-填空，4-评分，5-定位
    private Integer requireSig;//是否必填
    private String questionContent;//问题描述
    private Integer questionNo;//问题序号
    private String questionNote;//问题备注
    private Integer questionScore;//问题分数
}
