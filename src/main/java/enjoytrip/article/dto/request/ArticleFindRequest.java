package enjoytrip.article.dto.request;

import enjoytrip.article.domain.Type;
import lombok.*;

@Getter
@NoArgsConstructor
public class ArticleFindRequest {
    private Type type;
    private String title;
}
