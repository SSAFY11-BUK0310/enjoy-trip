package enjoytrip.article.service;

import enjoytrip.article.domain.Article;
import enjoytrip.article.dto.form.ArticleSaveForm;
import enjoytrip.article.dto.form.ArticleUpdateForm;
import enjoytrip.article.dto.request.ArticleFindRequest;
import enjoytrip.article.dto.response.ArticleFindResponse;
import enjoytrip.article.dto.response.ArticleSaveResponse;
import enjoytrip.article.dto.response.ArticleUpdateResponse;
import enjoytrip.article.exception.ArticleNotFoundException;
import enjoytrip.article.exception.ArticleSaveException;
import enjoytrip.article.exception.ArticleUpdateException;
import enjoytrip.article.repository.ArticleRepository;
import enjoytrip.article.util.RequestList;
import enjoytrip.article.util.file.FileStore;
import enjoytrip.article.util.file.UploadFile;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    public static final String NO_IMAGE_PNG = "no_image.png";
    private final ArticleRepository articleRepository;
    private final FileStore fileStore;


    public Page<ArticleFindResponse> findByPage(ArticleFindRequest request,
        Pageable pageable) {
        RequestList requestList = RequestList.builder()
            .type(request.getType())
            .title(request.getTitle())
            .pageable(pageable)
            .build();

        int total = articleRepository.count(Article.builder()
            .type(request.getType())
            .title(request.getTitle())
            .build());

        List<Article> content = articleRepository.findByPage(requestList);
        log.info("content={}", content.size());
        List<ArticleFindResponse> articleFindResponseList = getArticleFindResponseList(content);
        return new PageImpl<>(articleFindResponseList, pageable, total);
    }

    public ArticleFindResponse findById(Long id) {
        Article findArticle = articleRepository.findById(id)
            .orElseThrow(ArticleNotFoundException::new);
        return new ArticleFindResponse(findArticle);
    }

    public ArticleSaveResponse save(ArticleSaveForm articleSaveForm) throws Exception {

        // imageUUID 생성, 이미지 저장.
        UploadFile uploadFile = fileStore.storeFile(articleSaveForm.getImage());
        // uploadFile이 null이면 default image 넣어주자.
        if (uploadFile == null) {
            uploadFile = new UploadFile(NO_IMAGE_PNG, NO_IMAGE_PNG);
        }

        Article newArticle = Article.builder()
            .memberId(articleSaveForm.getMemberId())
            .title(articleSaveForm.getTitle())
            .content(articleSaveForm.getContent())
            .imageName(uploadFile.getUploadFileName())
            .imageUUID(uploadFile.getUploadFileUUID())
            .type(articleSaveForm.getType())
            .views(0)
            .address(articleSaveForm.getAddress())
            .createdAt(LocalDateTime.now())
            .createdBy("dummyEmail") // TODO: memberId로 member를 조회에서 해당 member 이메일을 넣자.
            .build();

        Long result = articleRepository.save(newArticle);
        if (result == 0) {
            throw new ArticleSaveException();
        }
        return new ArticleSaveResponse(newArticle.getId());
    }

    public ArticleUpdateResponse update(ArticleUpdateForm articleUpdateForm) throws Exception {

        // update할 이미지가 있다면 기존 이미지 삭제(no_image.png 제외)
        // 새로운 이미지에 대한 UUID 만들고 저장
        if (articleUpdateForm.getImage() != null) {
            if (!NO_IMAGE_PNG.equals(articleUpdateForm.getImageUUID())) {
                fileStore.deleteFile(articleUpdateForm.getImageUUID());
            }
            UploadFile uploadFile = fileStore.storeFile(articleUpdateForm.getImage());
            articleUpdateForm.addImageName(uploadFile.getUploadFileName());
            articleUpdateForm.addImageUUID(uploadFile.getUploadFileUUID());
        }

        Article newArticle = Article.builder()
            .id(articleUpdateForm.getId())
            .memberId(articleUpdateForm.getMemberId())
            .title(articleUpdateForm.getTitle())
            .content(articleUpdateForm.getContent())
            .imageName(articleUpdateForm.getImageName())
            .imageUUID(articleUpdateForm.getImageUUID())
            .views(articleUpdateForm.getViews())
            .address(articleUpdateForm.getAddress())
            .type(articleUpdateForm.getType())
            .updatedAt(LocalDateTime.now())
            .updatedBy("dummyEmail") // TODO: memberId로 member를 조회에서 해당 member 이메일을 넣자.
            .build();
        Long result = articleRepository.update(newArticle);
        if (result == 0) {
            throw new ArticleUpdateException();
        }
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
}
