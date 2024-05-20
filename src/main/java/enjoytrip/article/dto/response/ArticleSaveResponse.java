package enjoytrip.article.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSaveResponse {

    private Long id;

    public void addId(Long id) {
        this.id = id;
    }
}
