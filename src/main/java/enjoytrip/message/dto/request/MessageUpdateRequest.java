package enjoytrip.message.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageUpdateRequest {

  @NotNull
  private Long id;
  @NotNull
  private Long memberId;
  @NotBlank
  private String content;

  @Builder
  public MessageUpdateRequest(Long id, Long memberId, String content) {
    this.id = id;
    this.memberId = memberId;
    this.content = content;
  }
}
