package enjoytrip.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public class CommentSaveRequest {

  @NotNull
  private Long memberId;
  @NotNull
  private Long articleId;
  private Long parentId;
  @NotBlank
  private String content;

  @Builder
  public CommentSaveRequest(Long memberId, Long articleId, Long parentId, String content) {
    this.memberId = memberId;
    this.articleId = articleId;
    this.parentId = parentId;
    this.content = content;
  }
}
