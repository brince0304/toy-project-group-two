package com.toyboardproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE board SET is_deleted = true, deleted_at=now() WHERE id = ?")
@Entity
public class Board extends AuditingFields  {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String boardTitle;

    @Setter
    private String boardContent;

    @Builder.Default
    @Setter
    private Boolean isDeleted = Boolean.FALSE;

    private LocalDateTime deletedAt;

    @ManyToOne (fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Account account;

    @Builder.Default
    @OneToMany (mappedBy="board", cascade = CascadeType.ALL)
    private Set<BoardComment> boardComments = new LinkedHashSet<>();

    @Enumerated
    @Setter
    private BoardType boardType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id) && boardType == board.boardType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, boardType);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", boardTitle='" + boardTitle + '\'' +
                ", boardContent='" + boardContent + '\'' +
                ", isDeleted=" + isDeleted +
                ", deletedAt=" + deletedAt +
                ", account=" + account +
                ", boardComments=" + boardComments +
                ", boardType=" + boardType +
                '}';
    }
}