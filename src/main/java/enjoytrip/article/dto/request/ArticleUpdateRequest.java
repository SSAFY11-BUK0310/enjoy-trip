package enjoytrip.article.dto.request;

import enjoytrip.article.domain.Type;
import enjoytrip.article.dto.Base64Image;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ArticleUpdateRequest {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private String imageName;
    private String imageUUID;
    private Base64Image base64Image;
    private Integer views;
    private String address;
    private Type type;

    @Builder
    public ArticleUpdateRequest(Long id, Long memberId, String title, String content, String imageName, String imageUUID, Base64Image base64Image, Integer views,
                              String address, Type type) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.imageName = imageName;
        this.imageUUID = imageUUID;
        this.base64Image = base64Image;
        this.views = views;
        this.address = address;
        this.type = type;
    }
}
