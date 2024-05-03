package enjoytrip.member.dto;

import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberFindResponse {
    private Long id;
    private String email;
    private String password;
    private String name;
    private Integer age;
    private Gender gender;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private String createdBy;

    public MemberFindResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.name = member.getName();
        this.age = member.getAge();
        this.gender = member.getGender();
        this.phoneNumber = member.getPhoneNumber();
        this.createdAt = member.getCreatedAt();
        this.createdBy = member.getCreatedBy();
    }
}
