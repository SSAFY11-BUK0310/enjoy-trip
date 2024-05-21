package enjoytrip.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequest {

  @NotNull
  private Long id;
  @NotNull
  private Long memberId;
  @NotBlank
  private String content;

  @Builder
  public CommentUpdateRequest(Long id, Long memberId, String content) {
    this.id = id;
    this.memberId = memberId;
    this.content = content;
  }
}
