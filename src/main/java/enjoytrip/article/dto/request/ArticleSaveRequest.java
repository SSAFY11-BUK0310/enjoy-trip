package enjoytrip.article.dto.request;

import enjoytrip.article.domain.ArticleType;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleSaveRequest {

    private Long memberId;
    private String title;
    private String content;
    private List<String> selectedImages; // DB에 추가할 이미지 리스트
    private List<String> notSelectedImages; // 이미지 저장소에서    삭제해야 할 리스트
    private String directoryUUID; // 게시물 별 이미지 디렉토리명
    private String address;
    private ArticleType articleType;

    @Builder
    public ArticleSaveRequest(Long memberId, String title, String content,
        List<String> selectedImages, List<String> notSelectedImages, String directoryUUID,
        String address, ArticleType articleType) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.selectedImages = selectedImages;
        this.notSelectedImages = notSelectedImages;
        this.directoryUUID = directoryUUID;
        this.address = address;
        this.articleType = articleType;
    }
}
