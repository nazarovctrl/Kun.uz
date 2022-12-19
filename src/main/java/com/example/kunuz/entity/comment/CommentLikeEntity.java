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
        uniqueConstraints = @UniqueConstraint(columnNames = {"profile_id", "comment_id"}))
public class CommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private Integer commentId;
    @ManyToOne
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private CommentEntity comment;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

}
