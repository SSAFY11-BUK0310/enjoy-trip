package enjoytrip.like.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeDeleteRequest {

    private Long memberId;
    private Long articleId;

    @Builder
    public LikeDeleteRequest(Long memberId, Long articleId) {
        this.memberId = memberId;
        this.articleId = articleId;
    }
}
