package enjoytrip.member.mock;

import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.Member;
import enjoytrip.member.domain.RoleType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MockMember extends Member {

  private boolean isUpdated = false;

  public MockMember(Long id, String email, String password, String name, Integer age, Gender gender,
      String phoneNumber, RoleType roleType, LocalDateTime createAt, LocalDateTime updatedAt,
      String createdBy, String updatedBy) {
    super(id, email, password, name, age, gender, phoneNumber, roleType, createAt, updatedAt,
        createdBy, updatedBy);
  }

  @Override
  public void update(String email, String password, String name, Integer age, Gender gender,
      String phoneNumber, LocalDateTime updatedAt, String updatedBy) {
    super.update(email, password, name, age, gender, phoneNumber, updatedAt, updatedBy);
    isUpdated = true;
  }
}
