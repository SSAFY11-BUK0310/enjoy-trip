package enjoytrip.message.dto.response;

import lombok.Getter;

@Getter
public class MessageRoomSaveResponse {

  private Long id;

  public MessageRoomSaveResponse(Long id) {
    this.id = id;
  }
}
