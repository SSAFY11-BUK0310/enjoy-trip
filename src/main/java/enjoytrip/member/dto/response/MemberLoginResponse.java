package enjoytrip.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginResponse {

  private Long id;

  public MemberLoginResponse(Long id) {
    this.id = id;
  }
}
