package com.example.toeicwebsite.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Structure")
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "NVARCHAR(250)")
    private String name;

    @Column(name = "number_of_topic")
    private int number_of_topic;

    @Column(name = "level_of_topic", columnDefinition = "NVARCHAR(250)")
    private String level_of_topic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "part_id", nullable = false)
    private Part part;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kindOfStructure_id", nullable = false)
    private KindOfStructure kinfOfStructure;
}
