package enjoytrip.comment.mock;

import enjoytrip.comment.domain.Comment;
import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.Member;
import enjoytrip.member.domain.RoleType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MockComment extends Comment {

  private boolean isUpdated = false;

  public MockComment(Long id, Long memberId, Long articleId, Long parentId, String content,
      LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
    super(id, memberId, articleId, parentId, content, createdAt, updatedAt, createdBy, updatedBy);
  }

  public void update(String content, LocalDateTime updatedAt, String updatedBy) {
    isUpdated = true;
  }
}
