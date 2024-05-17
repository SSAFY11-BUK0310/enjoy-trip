package enjoytrip.article.dto.request;

import enjoytrip.article.domain.ArticleType;
import enjoytrip.article.dto.Base64Image;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleUpdateRequest {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private List<Base64Image> base64Images;
    private Integer views;
    private String address;
    private ArticleType articleType;

    @Builder
    public ArticleUpdateRequest(Long id, Long memberId, String title, String content,
        List<Base64Image> base64Images, Integer views, String address, ArticleType articleType) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.base64Images = base64Images;
        this.views = views;
        this.address = address;
        this.articleType = articleType;
    }
}
