package enjoytrip.member.mock;

import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.Member;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MockMember extends Member {

  private boolean isUpdated = false;

  public MockMember(Long id, String email, String password, String name, Integer age, Gender gender,
      String phoneNumber, LocalDateTime createAt, LocalDateTime updatedAt, String createdBy,
      String updatedBy) {
    super(id, email, password, name, age, gender, phoneNumber, createAt, updatedAt, createdBy,
        updatedBy);
  }

  @Override
  public void update(String email, String password, String name, Integer age, Gender gender,
      String phoneNumber, LocalDateTime updatedAt, String updatedBy) {
    super.update(email, password, name, age, gender, phoneNumber, updatedAt, updatedBy);
    isUpdated = true;
  }
}
