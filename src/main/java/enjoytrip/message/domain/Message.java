package enjoytrip.message.domain;

import enjoytrip.global.domain.BaseEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Message extends BaseEntity {

  private Long id;
  private Long memberId;
  private Long messageRoomId;
  private String content;

  @Builder
  public Message(Long id, Long memberId, Long messageRoomId, String content,
      LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
    super(createdAt, updatedAt, createdBy, updatedBy);
    this.id = id;
    this.memberId = memberId;
    this.messageRoomId = messageRoomId;
    this.content = content;
  }

  public void update(String content, String writer) {
    super.changeUpdatedAt(LocalDateTime.now());
    super.changeUpdatedBy(writer);
    this.content = content;
  }
}
