package com.example.SESummer.Entity;

import io.swagger.annotations.ApiImplicitParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "completion_question")
public class CompletionQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer completionQuestionID;//填空ID
    private Integer questionContentID;//所属题目ID
    private String answer;//填空题答案
}
