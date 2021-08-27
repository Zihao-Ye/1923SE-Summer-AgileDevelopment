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
    private Integer scoreQuestionID;//评分ID
    private Integer questionContentID;//所属题目ID
    private Integer maxScore;//评分最大值
    private Double averageScore;//平均分数
    private String startWord;//评分最低项
    private String endWord;//评分最高项
}
