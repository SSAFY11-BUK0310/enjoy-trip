package enjoytrip.article.dto.request;

import enjoytrip.article.domain.ArticleType;
import java.util.List;
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
    private List<String> selectedImages; // DB에 저장되어야 하는 이미지 목록
    private List<String> notSelectedImages; // 이미지 저장소에서 제거되어야 이미지 목록
    private String directoryUUID; // 게시물에 대한 이미지 저장소 디렉토리
    private Integer views;
    private String address;
    private ArticleType articleType;

    @Builder
    public ArticleUpdateRequest(Long id, Long memberId, String title, String content,
        List<String> selectedImages, List<String> notSelectedImages, String directoryUUID,
        Integer views, String address, ArticleType articleType) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.selectedImages = selectedImages;
        this.notSelectedImages = notSelectedImages;
        this.directoryUUID = directoryUUID;
        this.views = views;
        this.address = address;
        this.articleType = articleType;
    }
}
