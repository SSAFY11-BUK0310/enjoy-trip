package enjoytrip.member.domain;

import enjoytrip.global.domain.BaseEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member extends BaseEntity {

  private Long id;
  private String email;
  private String password;
  private String name;
  private Integer age;
  private Gender gender;
  private String phoneNumber;

  @Builder
  public Member(Long id, String email, String password, String name, Integer age, Gender gender,
      String phoneNumber, LocalDateTime createAt, LocalDateTime updatedAt, String createdBy,
      String updatedBy) {
    super(createAt, updatedAt, createdBy, updatedBy);
    this.id = id;
    this.email = email;
    this.password = password;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
  }

  public void update(String email, String password, String name, Integer age, Gender gender,
      String phoneNumber, LocalDateTime updatedAt, String updatedBy) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    changeUpdatedAt(updatedAt);
    changeUpdatedBy(updatedBy);
  }
}
