package enjoytrip.article.dto;

import enjoytrip.article.domain.Article;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ArticleUpdateResponse {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private String imageName;
    private String imageUUID;
    private Integer views;
    private String address;
    private String type;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;


    public ArticleUpdateResponse(Article article) {
        this.id = article.getId();
        this.memberId = article.getMemberId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.imageName = article.getImageName();
        this.imageUUID = article.getImageUUID();
        this.address = article.getAddress();
        this.type = article.getType();
        this.views = article.getViews();
        this.createdAt = article.getCreatedAt();
        this.createdBy = article.getCreatedBy();
        this.updatedAt = article.getUpdatedAt();
        this.updatedBy = article.getUpdatedBy();
    }
}
