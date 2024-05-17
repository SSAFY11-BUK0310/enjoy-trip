package enjoytrip.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class MemberPasswordUpdateRequest {

  private Long id;
  private String currentPassword;
  private String newPassword;
  private String checkPassword;

  @Builder
  public MemberPasswordUpdateRequest(Long id, String currentPassword, String newPassword,
      String checkPassword) {
    this.id = id;
    this.currentPassword = currentPassword;
    this.newPassword = newPassword;
    this.checkPassword = checkPassword;
  }
}
