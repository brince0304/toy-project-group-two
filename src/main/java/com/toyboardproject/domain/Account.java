package com.toyboardproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Builder
@Entity
@Getter
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE account SET is_deleted = true, deleted_at=now() WHERE id = ?")
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


    private LocalDateTime deletedAt;

    @OneToMany (mappedBy="account", cascade=CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private Set<Board> boards = new LinkedHashSet<>();


    @OneToMany (mappedBy="account", cascade=CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private Set<BoardComment> boardComments = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhoneNum='" + userPhoneNum + '\'' +
                ", isDeleted=" + isDeleted +
                ", deletedAt=" + deletedAt +
                ", boards=" + boards +
                ", boardComments=" + boardComments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
