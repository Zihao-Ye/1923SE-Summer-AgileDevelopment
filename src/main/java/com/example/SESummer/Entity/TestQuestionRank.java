package com.example.SESummer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test_question_rank")
public class TestQuestionRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testQuestionRankID;//题目显示顺序ID
    private Integer userID;//用户ID
    private Integer questionnaireID;//问卷ID
    private Integer questionContentID;//题目ID
    private Integer questionNo;//题目原序号
    private Integer showNo;//题目显示序号
}
