package enjoytrip.article.domain;

import enjoytrip.global.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * DOMAIN : 실제 DB의 테이블과 매칭시키는 클래스.
 */
@Getter
@ToString
public class Article extends BaseEntity {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private String imageName;
    private String imageUUID;
    private Integer views;
    private String address;
    private ArticleType articleType;

    @Builder
    public Article(Long id, Long memberId, String title, String content, String imageName,
        String imageUUID, Integer views, String address, ArticleType articleType,
        LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
        super(createdAt, updatedAt, createdBy, updatedBy);
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.imageName = imageName;
        this.imageUUID = imageUUID;
        this.views = views;
        this.address = address;
        this.articleType = articleType;
    }
}
