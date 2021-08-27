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
    private Integer questionnaireID;//问卷ID
    private String title;//问卷标题
    private Timestamp createTime;//创建时间
    private Integer recycleVolume;//收集数
    private Integer kind;//问卷种类：1-普通，2-投票，3-报名，4-考试，5-疫情防控
    private Integer isVisitable;//是否开启
    private Timestamp endTime;//截止时间
    private Integer masterID;//创建者ID
    private Integer isRubbish;//问卷垃圾状态：0-正常，1-回收站，2-在回收站中被删除
    private String questionnaireNote;//问卷说明
    private Integer havePublish;//是否发布过
    private String encryptQuestionnaireID;//加密后的问卷ID
    private String endMessage;//结束页信息
}
