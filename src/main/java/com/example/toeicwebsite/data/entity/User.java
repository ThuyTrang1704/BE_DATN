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
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", columnDefinition = "NVARCHAR(250)", nullable = false, unique = true)
    private String name;

    @Column(name = "email", columnDefinition = "NVARCHAR(250)", nullable = false, unique = true)
    private String email;

    @Column(name = "address", columnDefinition = "NVARCHAR(250)")
    private String address;

    @Column(name = "phoneNumber", columnDefinition = "NVARCHAR(250)")
    private String phoneNumber;

    @Column(name = "resetToken", columnDefinition = "NVARCHAR(250)")
    private String resetToken;

    @JsonIgnore
    @Column(name = "password", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    private boolean isDeleted = false;
}
