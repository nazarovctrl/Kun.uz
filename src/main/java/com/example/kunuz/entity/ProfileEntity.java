package com.example.kunuz.entity;

import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileRole role;


    @Column
    private Boolean visible=true;


    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();


}
