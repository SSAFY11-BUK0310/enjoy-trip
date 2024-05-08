package enjoytrip.member.dto.response;

import lombok.Getter;

@Getter
public class MemberUpdateResponse {

  private Long id;

  public MemberUpdateResponse(Long id) {
    this.id = id;
  }
}
