package enjoytrip.like.domain;

import enjoytrip.global.domain.BaseEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Like extends BaseEntity {

    private Long id;
    private Long memberId;
    private Long articleId;

    @Builder
    public Like(Long id, Long memberId, Long articleId,
        LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, updatedAt, createdBy, updatedBy);
        this.id = id;
        this.memberId = memberId;
        this.articleId = articleId;
    }
}
