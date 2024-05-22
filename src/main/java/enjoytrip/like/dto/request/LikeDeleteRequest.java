package enjoytrip.like.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeDeleteRequest {

    @NotNull
    private Long memberId;
    @NotNull
    private Long articleId;

    @Builder
    public LikeDeleteRequest(Long memberId, Long articleId) {
        this.memberId = memberId;
        this.articleId = articleId;
    }
}
