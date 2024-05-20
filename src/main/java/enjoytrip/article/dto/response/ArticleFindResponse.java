package enjoytrip.article.dto.response;


import enjoytrip.article.domain.Article;
import enjoytrip.article.domain.ArticleType;
import enjoytrip.global.image.domain.Image;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleFindResponse {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private Integer views;
    private String address;
    private ArticleType articleType;
    private String directoryUUID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private List<Image> images; // 게시물에 대한 이미지 목록

    public ArticleFindResponse(Article article) {
        this.id = article.getId();
        this.memberId = article.getMemberId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.views = article.getViews();
        this.address = article.getAddress();
        this.articleType = article.getArticleType();
        this.directoryUUID = article.getDirectoryUUID();
        this.createdAt = article.getCreatedAt();
        this.updatedAt = article.getUpdatedAt();
        this.createdBy = article.getCreatedBy();
        this.updatedBy = article.getUpdatedBy();
    }

    public void addImages(List<Image> images) {
        this.images = images;
    }
}
