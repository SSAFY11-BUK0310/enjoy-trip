package enjoytrip.article.dto.response;


import enjoytrip.article.domain.Article;
import enjoytrip.global.image.domain.Image;
import enjoytrip.article.domain.ArticleType;
import java.util.List;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleFindResponse {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private List<Image> articleImages;
    private Integer views;
    private String address;
    private ArticleType articleType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public ArticleFindResponse(Article article) {
        this.id = article.getId();
        this.memberId = article.getMemberId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.views = article.getViews();
        this.address = article.getAddress();
        this.articleType = article.getArticleType();
        this.createdAt = article.getCreatedAt();
        this.updatedAt = article.getUpdatedAt();
        this.createdBy = article.getCreatedBy();
        this.updatedBy = article.getUpdatedBy();
    }

    public void addArticleImages(List<Image> articleImages){
        this.articleImages = articleImages;
    }
}
