package enjoytrip.message.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageRoomSaveRequest {

  @NotNull
  private Long articleId;
  @NotNull
  private Long senderId;
  @NotNull
  private Long receiverId;

  public MessageRoomSaveRequest(Long articleId, Long senderId, Long receiverId) {
    this.articleId = articleId;
    this.senderId = senderId;
    this.receiverId = receiverId;
  }
}
