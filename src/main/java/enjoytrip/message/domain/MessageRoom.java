package enjoytrip.message.domain;

import enjoytrip.global.domain.BaseEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageRoom extends BaseEntity {

  private Long id;
  private Long articleId;
  private Long senderId;
  private Long receiverId;

  @Builder
  public MessageRoom(Long id, Long articleId, Long senderId, Long receiverId,
      LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
    super(createdAt, updatedAt, createdBy, updatedBy);
    this.id = id;
    this.articleId = articleId;
    this.senderId = senderId;
    this.receiverId = receiverId;
  }
}
