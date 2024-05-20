package enjoytrip.like.dto.response;

import lombok.Getter;

@Getter
public class LikeSaveResponse {

    private Long id;

    public void addId(Long id) {
        this.id = id;
    }

}
