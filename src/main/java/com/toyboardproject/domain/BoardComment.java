package com.toyboardproject.domain;

import com.toyboardproject.domain.account.Account;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Where(clause = "isDeleted = false")
@SQLDelete(sql = "UPDATE account SET isDeleted = true, deleted_at=now() WHERE id = ?")
public class BoardComment  extends AuditingFields {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String commentContent;

    @Setter
    private Boolean isDeleted = Boolean.FALSE;

    private LocalDateTime deletedAt;

    @ManyToOne (fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne (fetch = FetchType.LAZY)
    private Board board;


}
