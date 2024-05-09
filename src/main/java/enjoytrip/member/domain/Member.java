package enjoytrip.member.domain;

import enjoytrip.global.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member extends BaseEntity {

  private Long id;
  private String email;
  private String password;
  private String name;
  private Integer age;
  private Gender gender;
  private String phoneNumber;
  private RoleType roleType;

  @Builder
  public Member(Long id, String email, String password, String name, Integer age, Gender gender,
      String phoneNumber, RoleType roleType, LocalDateTime createAt, LocalDateTime updatedAt,
      String createdBy, String updatedBy) {
    super(createAt, updatedAt, createdBy, updatedBy);
    this.id = id;
    this.email = email;
    this.password = password;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.roleType = roleType;
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
