package enjoytrip.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginResponse {

  private Long id;
  private String name;

  public MemberLoginResponse(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
