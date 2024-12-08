package com.example.toeicwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "totalMark", columnDefinition = "NVARCHAR(250)")
    private String totalMark;

    @Column(name = "status", columnDefinition = "NVARCHAR(250)")
    private String status;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "structureId")
    private Long structureId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
