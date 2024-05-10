package enjoytrip.article.dto.form;

import enjoytrip.article.domain.ArticleType;
import enjoytrip.article.dto.request.ArticleSaveRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ArticleSaveForm {

    private Long memberId;
    private String title;
    private String content;
    private MultipartFile image;
    private String address;
    private ArticleType articleType;

    public ArticleSaveForm(ArticleSaveRequest request) {
        this.memberId = request.getMemberId();
        this.title = request.getTitle();
        this.content = request.getContent();
        this.address = request.getAddress();
        this.articleType = request.getArticleType();
    }

    public void addImage(MultipartFile image) {
        this.image = image;
    }
}
