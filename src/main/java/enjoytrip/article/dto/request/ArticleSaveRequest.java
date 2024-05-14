package enjoytrip.article.dto.request;

import enjoytrip.article.domain.ArticleType;
import enjoytrip.article.dto.Base64Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleSaveRequest {

    private Long memberId;
    private String title;
    private String content;
    private Base64Image base64Image;
    private String address;
    private ArticleType articleType;

    @Builder
    public ArticleSaveRequest(Long memberId, String title, String content, Base64Image base64Image,
        String address, ArticleType articleType) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.base64Image = base64Image;
        this.address = address;
        this.articleType = articleType;
    }

}
