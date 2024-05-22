package enjoytrip.message.dto.response;

import enjoytrip.message.domain.Message;
import enjoytrip.message.domain.MessageRoom;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MessageRoomFindResponse {

  private Long id;
  private Long articleId;
  private Long senderId;
  private Long receiverId;
  private LocalDateTime createdAt;
  private String createdBy;

  public MessageRoomFindResponse(MessageRoom messageRoom) {
    this.id = messageRoom.getId();
    this.articleId = messageRoom.getArticleId();
    this.senderId = messageRoom.getSenderId();
    this.receiverId = messageRoom.getReceiverId();
    this.createdAt = messageRoom.getCreatedAt();
    this.createdBy = messageRoom.getCreatedBy();
  }
}
