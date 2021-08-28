package com.example.SESummer.Entity;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_locate_question")
public class UserLocateQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userLocateQuestionID;//定位题答题记录
    private Integer userID;//用户ID
    private Integer questionnaireID;//问卷ID
    private Integer questionContentID;//问题ID
    private String locate;//填写的位置信息
}
