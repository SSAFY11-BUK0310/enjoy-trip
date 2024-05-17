package enjoytrip.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequest {

  private Long memberId;
  private Long articleId;
  private Long childId;
  private String content;
  private String createdBy;
  private String updatedBy;

  public CommentSaveRequest(Long memberId, Long articleId, Long childId, String content,
      String createdBy, String updatedBy) {
    this.memberId = memberId;
    this.articleId = articleId;
    this.childId = childId;
    this.content = content;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
  }
}
