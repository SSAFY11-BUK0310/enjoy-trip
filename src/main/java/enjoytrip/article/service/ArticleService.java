package enjoytrip.article.service;

import static enjoytrip.global.exception.ErrorCode.ARTICLE_NOT_FOUND;

import enjoytrip.article.domain.Article;
import enjoytrip.article.domain.ArticleType;
import enjoytrip.article.dto.Base64Image;
import enjoytrip.article.dto.request.ArticleSaveRequest;
import enjoytrip.article.dto.request.ArticleUpdateRequest;
import enjoytrip.article.dto.response.ArticleFindResponse;
import enjoytrip.article.dto.response.ArticleSaveResponse;
import enjoytrip.article.dto.response.ArticleUpdateResponse;
import enjoytrip.article.exception.ArticleNotFoundException;
import enjoytrip.article.repository.ArticleRepository;
import enjoytrip.article.util.file.FileStore;
import enjoytrip.article.util.file.UploadFile;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    public static final String NO_IMAGE_PNG = "no_image.png";
    private final ArticleRepository articleRepository;
    private final FileStore fileStore;

    public ArticleSaveResponse save(ArticleSaveRequest request) throws Exception {

        MultipartFile image = getMultipartFile(request.getBase64Image());

        // imageUUID 생성, 이미지 저장.
        UploadFile uploadFile = fileStore.storeFile(image);

        // uploadFile이 null이면 default image 넣어주자.
        if (uploadFile == null) {
            uploadFile = new UploadFile(NO_IMAGE_PNG, NO_IMAGE_PNG);
        }

        Article newArticle = Article.builder()
            .memberId(request.getMemberId())
            .title(request.getTitle())
            .content(request.getContent())
            .imageName(uploadFile.getUploadFileName())
            .imageUUID(uploadFile.getUploadFileUUID())
            .articleType(request.getArticleType())
            .views(0)
            .address(request.getAddress())
            .createdAt(LocalDateTime.now())
            .createdBy("dummyEmail") // TODO: memberId로 member를 조회에서 해당 member 이메일을 넣자.
            .build();

        // TODO: DB 반환 값에 대한 내용 응답에 추가하기.
        articleRepository.save(newArticle);
        return new ArticleSaveResponse(newArticle.getId());
    }

    public Page<ArticleFindResponse> findByPage(ArticleType articleType, String title,
        Pageable pageable) {
        int total = articleRepository.count(articleType, title);
        List<Article> content = articleRepository.findByPage(articleType, title, pageable);
        List<ArticleFindResponse> articleFindResponseList = getArticleFindResponseList(content);
        return new PageImpl<>(articleFindResponseList, pageable, total);
    }

    public ArticleFindResponse findById(Long id) {
        Article findArticle = articleRepository.findById(id).orElseThrow(
            () -> new ArticleNotFoundException(ARTICLE_NOT_FOUND, "does not exist article"));
        return new ArticleFindResponse(findArticle);
    }

    public ArticleUpdateResponse update(ArticleUpdateRequest request) throws Exception {

        MultipartFile image = getMultipartFile(request.getBase64Image());

        // update할 이미지가 있다면 기존 이미지 삭제(no_image.png 제외)
        // 새로운 이미지에 대한 UUID 만들고 저장
        if (image != null) {
            if (!NO_IMAGE_PNG.equals(request.getImageUUID())) {
                fileStore.deleteFile(request.getImageUUID());
            }
            UploadFile uploadFile = fileStore.storeFile(image);
            request.addImageName(uploadFile.getUploadFileName());
            request.addImageUUID(uploadFile.getUploadFileUUID());
        }

        Article newArticle = Article.builder()
            .id(request.getId())
            .memberId(request.getMemberId())
            .title(request.getTitle())
            .content(request.getContent())
            .imageName(request.getImageName())
            .imageUUID(request.getImageUUID())
            .views(request.getViews())
            .address(request.getAddress())
            .articleType(request.getArticleType())
            .updatedAt(LocalDateTime.now())
            .updatedBy("dummyEmail") // TODO: memberId로 member를 조회에서 해당 member 이메일을 넣자.
            .build();

        // TODO: DB 반환 값에 대한 내용 응답에 추가하기.
        articleRepository.update(newArticle);
        // TODO: 예외 발생 시 갱신을 위해 저장한 이미지 삭제, 원 이미지 복원
        return new ArticleUpdateResponse(newArticle.getId());
    }

    public void delete(Long id) {
        String imageUUID = findById(id).getImageUUID();
        if (!NO_IMAGE_PNG.equals(imageUUID)) {
            fileStore.deleteFile(imageUUID);
        }
        articleRepository.delete(id);
    }

    private List<ArticleFindResponse> getArticleFindResponseList(List<Article> content) {
        List<ArticleFindResponse> articleFindResponseList = new ArrayList<>();
        for (Article article : content) {
            articleFindResponseList.add(new ArticleFindResponse(article));
        }
        return articleFindResponseList;
    }

    private MultipartFile getMultipartFile(Base64Image base64Image) {
        if (base64Image == null) {
            return null;
        }
        String fileName = base64Image.getOriginalName();
        String base64File = base64Image.getBase64File();
        String extension = base64Image.getExtension();

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByte = decoder.decode(base64File.getBytes());

        return new MockMultipartFile("image", fileName, extension, decodedByte);
    }
}
