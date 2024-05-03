package enjoytrip.member.dto;

import lombok.Getter;

@Getter
public class MemberSaveResponse {
    private Long id;

    public MemberSaveResponse(Long id) {
        this.id = id;
    }
}
