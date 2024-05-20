package enjoytrip.member.dto.request;

import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequest {

  @NotBlank(message = "{NotBlank.email}")
  @Email(message = "{Email.email}")
  private String email;
  @NotBlank(message = "{NotBlank.password}")
  @Size(min = 8, max = 16, message = "{Size.password}")
  private String password;
  @NotBlank(message = "{NotBlank.name}")
  @Size(min = 1, max = 10, message = "{Size.name}")
  private String name;
  @NotNull(message = "{NotNull.age}")
  @Positive(message = "{Positive.age}")
  private Integer age;
  @NotNull(message = "{NotNull.gender}")
  private Gender gender;
  @NotBlank(message = "{NotBlank.phoneNumber}")
  @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "{Pattern.phoneNumber}")
  private String phoneNumber;
  @NotNull(message = "{NotNull.roleType}")
  private RoleType roleType;
  @NotBlank(message = "{NotBlank.createdBy}")
  private String createdBy;
  @NotBlank(message = "{NotBlank.updatedBy}")
  private String updatedBy;

  @Builder
  public MemberSaveRequest(String email, String password, String name, Integer age, Gender gender,
      String phoneNumber, RoleType roleType, String createdBy, String updatedBy) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.roleType = roleType;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
  }
}
