package enjoytrip.comment.dto.response;

import enjoytrip.comment.domain.Comment;
import java.time.LocalDateTime;

public class CommentFindResponse {

  private Long id;
  private Long memberId;
  private Long articleId;
  private Long childId;
  private String content;
  private LocalDateTime createdAt;
  private String createdBy;

  public CommentFindResponse(Comment comment) {
    this.id = comment.getId();
    this.memberId = comment.getMemberId();
    this.articleId = comment.getArticleId();
    this.childId = comment.getChildId();
    this.content = comment.getContent();
    this.createdAt = comment.getCreatedAt();
    this.createdBy = comment.getCreatedBy();
  }
}
