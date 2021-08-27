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
    private Integer questionOptionID;//选项ID
    private Integer optionKind;//选项类型：1-普通选择题选项，2-报名问卷报名选择题的选项，3-考试有分数的选择题的选项
    private Integer questionContentID;//选项所属题目ID
    private String optionContent;//选项内容
    private Integer voteVolume;//选项选择人数
    private Integer leftVolume;//选项最大可选数
    private Integer isAnswer;//该选项是否是选择题的答案：0-不是，1-是
    private Integer optionNo;//选项序号
}
