package enjoytrip.message.mock;

import enjoytrip.message.domain.Message;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MockMessage extends Message {

  private boolean isUpdated = false;

  public MockMessage(Long id, Long memberId, Long messageRoomId, String content,
      LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
    super(id, memberId, messageRoomId, content, createdAt, updatedAt, createdBy, updatedBy);
  }

  public void update(String content, String writer) {
    isUpdated = true;
  }
}
