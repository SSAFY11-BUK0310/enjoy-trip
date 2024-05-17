package enjoytrip.global.image.domain;

import enjoytrip.global.domain.BaseEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Image extends BaseEntity {

    private Long id;
    private Long articleId;
    private String imageName;
    private String imageUUID;

    @Builder
    public Image(Long id, Long articleId, String imageName, String imageUUID,
        LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
        super(createdAt, updatedAt, createdBy, updatedBy);
        this.id = id;
        this.articleId = articleId;
        this.imageName = imageName;
        this.imageUUID = imageUUID;
    }
}
