package enjoytrip.article.dto.request;

import enjoytrip.article.domain.ArticleType;
import enjoytrip.article.dto.Base64Image;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleSaveRequest {

    private Long memberId;
    private String title;
    private String content;
    private List<Base64Image> base64Images;
    private String address;
    private ArticleType articleType;

    @Builder
    public ArticleSaveRequest(Long memberId, String title, String content,
        List<Base64Image> base64Images,
        String address, ArticleType articleType) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.base64Images = base64Images;
        this.address = address;
        this.articleType = articleType;
    }

}
