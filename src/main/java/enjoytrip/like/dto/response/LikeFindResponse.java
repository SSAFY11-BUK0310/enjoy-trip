package enjoytrip.like.dto.response;

import enjoytrip.like.domain.Like;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class LikeFindResponse {

    private Long id;
    private Long memberId;
    private Long articleId;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    public LikeFindResponse(Like like) {
        this.id = like.getId();
        this.memberId = like.getMemberId();
        this.articleId = like.getArticleId();
        this.createdAt = like.getCreatedAt();
        this.createdBy = like.getCreatedBy();
        this.updatedAt = like.getUpdatedAt();
        this.updatedBy = like.getUpdatedBy();
    }
}
