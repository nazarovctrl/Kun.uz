package com.example.kunuz.entity.comment;


import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "comment_like",
        uniqueConstraints = @UniqueConstraint(columnNames = {"profile_id","comment_id"}))
public class CommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

}
