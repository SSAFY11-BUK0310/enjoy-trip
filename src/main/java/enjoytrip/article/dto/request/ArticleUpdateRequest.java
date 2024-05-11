package enjoytrip.article.dto.request;

import enjoytrip.article.domain.ArticleType;
import enjoytrip.article.dto.Base64Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private ArticleType articleType;

    @Builder
    public ArticleUpdateRequest(Long id, Long memberId, String title, String content,
        String imageName, String imageUUID, Base64Image base64Image, Integer views,
        String address, ArticleType articleType) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.imageName = imageName;
        this.imageUUID = imageUUID;
        this.base64Image = base64Image;
        this.views = views;
        this.address = address;
        this.articleType = articleType;
    }

    public void addImageName(String imageName) {
        this.imageName = imageName;
    }

    public void addImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
    }
}
