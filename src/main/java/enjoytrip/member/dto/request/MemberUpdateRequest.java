package enjoytrip.member.dto.request;

import enjoytrip.member.domain.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberUpdateRequest {
    private Long id;
    private String email;
    private String password;
    private String name;
    private Integer age;
    private Gender gender;
    private String phoneNumber;
    private LocalDateTime updatedAt;
    private String updatedBy;

    @Builder
    public MemberUpdateRequest(Long id, String email, String password, String name, Integer age, Gender gender, String phoneNumber, LocalDateTime updatedAt, String updatedBy) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }
}