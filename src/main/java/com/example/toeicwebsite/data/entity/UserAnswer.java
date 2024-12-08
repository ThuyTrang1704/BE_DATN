package com.example.toeicwebsite.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UserAnswer")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_choice", columnDefinition = "NVARCHAR(250)")
    private String answerChoice;

    @Column(name = "answer_number")
    private int AnswerNumber;

    @Column(name = "mark")
    private int mark;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "result_id", nullable = false)
    private Result result;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_detail_id", nullable = false)
    private TestDetail testDetail;
}
