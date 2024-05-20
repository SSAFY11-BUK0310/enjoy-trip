package enjoytrip.member.dto.request;

import enjoytrip.member.domain.Gender;
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
public class MemberUpdateRequest {

  private Long id;
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
  @NotBlank(message = "{NotBlank.updatedBy}")
  private String updatedBy;

  @Builder
  public MemberUpdateRequest(Long id, String name, Integer age,
      Gender gender, String phoneNumber, String updatedBy) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.updatedBy = updatedBy;
  }
}
