package enjoytrip.article.domain;

import enjoytrip.global.domain.BaseEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
    private Integer views;
    private String address;
    private ArticleType articleType;

    @Builder
    public Article(Long id, Long memberId, String title, String content, Integer views, String address, ArticleType articleType,
        LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
        super(createdAt, updatedAt, createdBy, updatedBy);
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.views = views;
        this.address = address;
        this.articleType = articleType;
    }
}
