package enjoytrip.message.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageSaveRequest {

  @NotNull
  private Long memberId;
  @NotNull
  private Long messageRoomId;
  @NotBlank
  private String content;

  @Builder
  public MessageSaveRequest(Long memberId, Long messageRoomId, String content) {
    this.memberId = memberId;
    this.messageRoomId = messageRoomId;
    this.content = content;
  }
}
