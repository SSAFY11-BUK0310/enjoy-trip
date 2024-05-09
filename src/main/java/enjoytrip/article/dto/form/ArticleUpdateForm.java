package enjoytrip.article.dto.form;

import enjoytrip.article.domain.Type;
import enjoytrip.article.dto.request.ArticleUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ArticleUpdateForm {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private String imageName;
    private String imageUUID;
    private MultipartFile image;
    private Integer views;
    private String address;
    private Type type;

    public ArticleUpdateForm(ArticleUpdateRequest request) {
        this.id = request.getId();
        this.memberId = request.getMemberId();
        this.title = request.getTitle();
        this.content = request.getContent();
        this.imageName = request.getImageName();
        this.imageUUID = request.getImageUUID();
        this.views = request.getViews();
        this.address = request.getAddress();
        this.type = request.getType();
    }

    public void addImage(MultipartFile image) {
        this.image = image;
    }

    public void addImageName(String imageName) {
        this.imageName = imageName;
    }

    public void addImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
    }
}
