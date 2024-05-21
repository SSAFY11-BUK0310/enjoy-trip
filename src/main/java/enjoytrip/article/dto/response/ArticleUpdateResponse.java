package enjoytrip.article.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleUpdateResponse {

    private Long id;

    public void addId(Long id) {
        this.id = id;
    }
}
