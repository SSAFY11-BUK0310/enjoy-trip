package enjoytrip.member.mock;

import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.Member;
import enjoytrip.member.domain.RoleType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MockMember extends Member {

  private boolean isUpdated = false;

  public MockMember(Long id, String email, String password, String name, Integer age, Gender gender,
      String phoneNumber, RoleType roleType, LocalDateTime createAt, LocalDateTime updatedAt,
      String createdBy, String updatedBy) {
    super(id, email, password, name, age, gender, roleType, phoneNumber, createAt, updatedAt,
        createdBy, updatedBy);
  }

  public void updateInfo(String name, Integer age, Gender gender,
      String phoneNumber, LocalDateTime updatedAt, String updatedBy) {
    isUpdated = true;
  }

  public void updatePassword(String password) {
    isUpdated = true;
  }
}
