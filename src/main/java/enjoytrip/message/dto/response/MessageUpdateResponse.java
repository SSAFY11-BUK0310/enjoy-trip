package enjoytrip.message.dto.response;

import lombok.Getter;

@Getter
public class MessageUpdateResponse {

  private Long id;

  public MessageUpdateResponse(Long id) {
    this.id = id;
  }
}
