package com.toyboardproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE board_comment SET is_deleted = true, deleted_at=now() WHERE id = ?")
public class BoardComment  extends AuditingFields {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String commentContent;

    @Builder.Default
    @Setter
    private Boolean isDeleted = Boolean.FALSE;

    private LocalDateTime deletedAt;

    @ManyToOne (fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne (fetch = FetchType.LAZY)
    private Board board;

    public void changeCommentContent(String commentContent){
        this.commentContent = commentContent;
    }

}
