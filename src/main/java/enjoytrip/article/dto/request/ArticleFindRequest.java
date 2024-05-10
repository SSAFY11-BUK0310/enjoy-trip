package enjoytrip.article.dto.request;

import enjoytrip.article.domain.ArticleType;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFindRequest {

    private ArticleType articleType;
    private String title;
}
