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
    private String directoryUUID;

    @Builder
    public Image(Long id, Long articleId, String imageName, String imageUUID, String directoryUUID,
        LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, updatedAt, createdBy, updatedBy);
        this.id = id;
        this.articleId = articleId;
        this.imageName = imageName;
        this.imageUUID = imageUUID;
        this.directoryUUID = directoryUUID;
    }
}
