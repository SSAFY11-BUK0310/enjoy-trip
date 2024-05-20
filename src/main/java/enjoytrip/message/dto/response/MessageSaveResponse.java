package enjoytrip.message.dto.response;

import lombok.Getter;

@Getter
public class MessageSaveResponse {

  private Long id;

  public MessageSaveResponse(Long id) {
    this.id = id;
  }
}
