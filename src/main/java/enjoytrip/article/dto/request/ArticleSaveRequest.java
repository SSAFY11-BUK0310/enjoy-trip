package enjoytrip.article.dto.request;

import enjoytrip.article.domain.ArticleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleSaveRequest {

    private Long memberId;
    @NotBlank(message = "{NotBlank.title}")
    private String title;
    @NotBlank(message = "{NotBlank.content}")
    private String content;
    @NotNull(message = "{NotNull}")
    private List<String> selectedImages; // DB에 추가할 이미지 리스트
    @NotNull
    private List<String> notSelectedImages; // 이미지 저장소에서    삭제해야 할 리스트
    @NotNull
    private String directoryUUID; // 게시물 별 이미지 디렉토리명
    private String address;
    @NotNull(message = "{NotBlank}")
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
