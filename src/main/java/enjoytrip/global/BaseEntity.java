package enjoytrip.global;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseEntity {

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String createdBy;
  private String updatedBy;

  public BaseEntity(LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy,
      String updatedBy) {
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
  }

  public void changeUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void changeUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }
}
