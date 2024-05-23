package enjoytrip.message.dto.response;

import enjoytrip.message.domain.MessageRoom;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MessageRoomFindResponse {

  private Long id;
  private Long articleId;
  private String articleTitle;
  private Long senderId;
  private String senderEmail;
  private Long receiverId;
  private String receiverEmail;
  private LocalDateTime createdAt;
  private String createdBy;

  public MessageRoomFindResponse(MessageRoom messageRoom, String articleTitle,
      String receiverEmail) {
    this.id = messageRoom.getId();
    this.articleId = messageRoom.getArticleId();
    this.articleTitle = articleTitle;
    this.senderId = messageRoom.getSenderId();
    this.senderEmail = messageRoom.getCreatedBy();
    this.receiverId = messageRoom.getReceiverId();
    this.receiverEmail = receiverEmail;
    this.createdAt = messageRoom.getCreatedAt();
    this.createdBy = messageRoom.getCreatedBy();
  }
}
