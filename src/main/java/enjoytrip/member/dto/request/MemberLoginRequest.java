package enjoytrip.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {

  @NotBlank(message = "{NotBlank.email}")
  @Email(message = "{Email.email}")
  private String email;
  @NotBlank(message = "{NotBlank.password}")
  @Size(min = 8, max = 16, message = "{Size.password}")
  private String password;

  public MemberLoginRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
