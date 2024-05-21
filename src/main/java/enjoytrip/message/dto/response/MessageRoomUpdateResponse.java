package enjoytrip.message.dto.response;

import lombok.Getter;

@Getter
public class MessageRoomUpdateResponse {

  private Long id;

  public MessageRoomUpdateResponse(Long id) {
    this.id = id;
  }
}
