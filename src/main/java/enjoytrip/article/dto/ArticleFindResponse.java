package enjoytrip.article.dto;


import enjoytrip.article.domain.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleFindResponse {
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
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public ArticleFindResponse(Article article) {
        this.id = article.getId();
        this.memberId = article.getMemberId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.imageName = article.getImageName();
        this.imageUUID = article.getImageUUID();
        this.views = article.getViews();
        this.address = article.getAddress();
        this.type = article.getType();
        this.createdAt = article.getCreatedAt();
        this.updatedAt = article.getUpdatedAt();
        this.createdBy = article.getCreatedBy();
        this.updatedBy = article.getUpdatedBy();
    }
}
