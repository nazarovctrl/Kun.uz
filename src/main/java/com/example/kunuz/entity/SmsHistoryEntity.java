package com.example.kunuz.entity;

import com.example.kunuz.enums.SmsHistoryStatus;
import com.example.kunuz.enums.SmsHistoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sms_history")

@Getter
@Setter
public class SmsHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column
    private String phone;

    @Column
    private String message;

    @Column
    @Enumerated(EnumType.STRING)
    private SmsHistoryStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private SmsHistoryType type;

    @Column(name = "created_date")
    private LocalDateTime createdDate;


}
