package com.example.SESummer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "test_score_record")
public class TestScoreRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testScoreRecordID;//考试分数记录ID
    private Integer userID;//用户ID
    private Integer questionContentID;//记录对应的问题ID
    private Integer Score;//该问题得分
}
