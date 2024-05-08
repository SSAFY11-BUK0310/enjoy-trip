package enjoytrip.member.dto.request;

import enjoytrip.member.domain.Gender;
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
  private String createdBy;
  private String updatedBy;

  @Builder
  public MemberSaveRequest(String email, String password, String name, Integer age, Gender gender,
      String phoneNumber, String createdBy, String updatedBy) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
  }
}
