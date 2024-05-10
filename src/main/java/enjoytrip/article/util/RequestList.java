package enjoytrip.article.util;

import enjoytrip.article.domain.ArticleType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;


@Data
public class RequestList {

    private ArticleType articleType;
    private String title;
    private Pageable pageable;

    @Builder
    public RequestList(ArticleType articleType, String title, Pageable pageable) {
        this.articleType = articleType;
        this.title = title;
        this.pageable = pageable;
    }
}
