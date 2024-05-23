package enjoytrip.message.dto.response;

import enjoytrip.message.domain.Message;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MessageFindResponse {


  private Long id;
  private Long memberId;
  private Long message_room_id;
  private String content;
  private LocalDateTime createdAt;
  private String createdBy;

  public MessageFindResponse(Message message) {
    this.id = message.getId();
    this.memberId = message.getMemberId();
    this.message_room_id = message.getMessageRoomId();
    this.content = message.getContent();
    this.createdAt = message.getCreatedAt();
    this.createdBy = message.getCreatedBy();
  }
}
