package enjoytrip.comment.domain;

import enjoytrip.global.domain.BaseEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment extends BaseEntity {

  private Long id;
  private Long memberId;
  private Long articleId;
  private Long childId;
  private String content;

  @Builder
  public Comment(Long id, Long memberId, Long articleId, Long childId, String content,
      LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
    super(createdAt, updatedAt, createdBy, updatedBy);
    this.id = id;
    this.memberId = memberId;
    this.articleId = articleId;
    this.childId = childId;
    this.content = content;
  }

  public void update(String content, LocalDateTime updatedAt, String updatedBy) {
    super.changeUpdatedAt(updatedAt);
    super.changeUpdatedBy(updatedBy);
    this.content = content;
  }
}
