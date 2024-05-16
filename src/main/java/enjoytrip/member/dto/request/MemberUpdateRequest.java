package enjoytrip.member.dto.request;

import enjoytrip.member.domain.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {

  private Long id;
  private String name;
  private Integer age;
  private Gender gender;
  private String phoneNumber;
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
