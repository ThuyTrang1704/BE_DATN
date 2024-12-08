package com.example.toeicwebsite.data.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "NVARCHAR(250)")
    private String name;

    @Column(name = "content", columnDefinition = "NVARCHAR(250)")
    private String content;

    @Column(name = "audio_name", columnDefinition = "NVARCHAR(250)")
    private String audio_name;

    @Column(name = "image_name", columnDefinition = "NVARCHAR(250)")
    private String image_name;

    @Column(name = "audio_path", columnDefinition = "NVARCHAR(250)")
    private String audio_path;

    @Column(name = "image_path", columnDefinition = "NVARCHAR(250)")
    private String image_path;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "part_id", nullable = false)
    private Part part;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    private boolean isDeleted = false;

}
