package enjoytrip.comment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequest {

  private Long memberId;
  private Long articleId;
  private Long parentId;
  private String content;

  @Builder
  public CommentSaveRequest(Long memberId, Long articleId, Long parentId, String content) {
    this.memberId = memberId;
    this.articleId = articleId;
    this.parentId = parentId;
    this.content = content;
  }
}
