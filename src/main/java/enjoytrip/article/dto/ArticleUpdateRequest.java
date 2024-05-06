package enjoytrip.article.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ArticleUpdateRequest {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private String imageName;
    private String imageUUID;
    private Integer views;
    private String address;
    private String type;
    private LocalDateTime updatedAt;
    private String updatedBy;

    @Builder
    public ArticleUpdateRequest(Long id, Long memberId, String title, String content, String imageName, String imageUUID, Integer views,
                              String address, String type, LocalDateTime updatedAt, String updatedBy) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.imageName = imageName;
        this.imageUUID = imageUUID;
        this.views = views;
        this.address = address;
        this.type = type;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }
}
