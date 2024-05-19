package enjoytrip.comment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequest {

  private Long id;
  private Long memberId;
  private String content;

  @Builder
  public CommentUpdateRequest(Long id, Long memberId, String content) {
    this.id = id;
    this.memberId = memberId;
    this.content = content;
  }
}
