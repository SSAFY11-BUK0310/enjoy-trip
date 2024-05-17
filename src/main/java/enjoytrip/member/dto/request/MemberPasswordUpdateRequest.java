package enjoytrip.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class MemberPasswordUpdateRequest {

  private Long id;
  @NotBlank(message = "{NotBlank.password}")
  @Size(min = 8, max = 16, message = "{Size.password}")
  private String currentPassword;
  @NotBlank(message = "{NotBlank.password}")
  @Size(min = 8, max = 16, message = "{Size.password}")
  private String newPassword;
  @NotBlank(message = "{NotBlank.password}")
  @Size(min = 8, max = 16, message = "{Size.password}")
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
