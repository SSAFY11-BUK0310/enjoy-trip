package enjoytrip.like.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeSaveResponse {

    private Long id;

    public void addId(Long id) {
        this.id = id;
    }
}
