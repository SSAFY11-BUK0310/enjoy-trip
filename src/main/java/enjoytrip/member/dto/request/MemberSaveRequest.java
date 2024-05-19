package enjoytrip.member.dto.request;

import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberSaveRequest {

  private String email;
  private String password;
  private String name;
  private Integer age;
  private Gender gender;
  private String phoneNumber;
  private RoleType roleType;

  @Builder
  public MemberSaveRequest(String email, String password, String name, Integer age, Gender gender,
      String phoneNumber, RoleType roleType) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.roleType = roleType;
  }
}
