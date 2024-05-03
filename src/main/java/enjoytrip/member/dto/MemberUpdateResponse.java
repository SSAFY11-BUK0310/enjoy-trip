package enjoytrip.member.dto;

import lombok.Getter;

@Getter
public class MemberUpdateResponse {
    private Long id;

    public MemberUpdateResponse(Long id) {
        this.id = id;
    }
}
