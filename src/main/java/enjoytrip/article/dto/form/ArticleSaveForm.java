package enjoytrip.article.dto.form;

import enjoytrip.article.domain.Type;
import enjoytrip.article.dto.Base64Image;
import enjoytrip.article.dto.request.ArticleSaveRequest;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ArticleSaveForm {

    private Long memberId;
    private String title;
    private String content;
    private MultipartFile image;
    private String address;
    private Type type;

    public ArticleSaveForm(ArticleSaveRequest request) {
        this.memberId = request.getMemberId();
        this.title = request.getTitle();
        this.content = request.getContent();
        this.address = request.getAddress();
        this.type = request.getType();
    }

    public void addImage(MultipartFile image) {
        this.image = image;
    }
}
