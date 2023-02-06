package com.toyboardproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Where(clause = "isDeleted = false")
@SQLDelete(sql = "UPDATE account SET isDeleted = true, deleted_at=now() WHERE id = ?")
@Builder
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class Account extends AuditingFields {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String userId;

    @Setter
    private String userPassword;


    @Setter
    private String userNickname;

    @Setter
    private String userName;

    @Setter
    private String userPhoneNum;

    @Setter
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;


    @Nullable
    private LocalDateTime deletedAt;

    @OneToMany (mappedBy="account", cascade=CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private Set<Board> boards = new LinkedHashSet<>();


    @OneToMany (mappedBy="account", cascade=CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private Set<BoardComment> boardComments = new LinkedHashSet<>();
}
