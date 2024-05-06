package enjoytrip.article.dto;

import enjoytrip.article.util.file.UploadFile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ArticleSaveRequest {
    private Long memberId;
    private String title;
    private String content;
    private MultipartFile uploadImage;
    private String address;
    private String type;
    private LocalDateTime createdAt;

    @Builder
    public ArticleSaveRequest(Long memberId, String title, String content, MultipartFile uploadImage,
                              String address, String type, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.uploadImage = uploadImage;
        this.address = address;
        this.type = type;
        this.createdAt = createdAt;
    }
}
