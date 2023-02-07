package com.toyboardproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE account SET is_deleted = true, deleted_at=now() WHERE id = ?")
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

    @ManyToOne (fetch = FetchType.LAZY)
    private Account account;

    @Builder.Default
    @OneToMany (mappedBy="board", cascade=CascadeType.ALL)
    private Set<BoardComment> boardComments = new LinkedHashSet<>();

    @Enumerated
    @Setter
    private BoardType boardType;
}
