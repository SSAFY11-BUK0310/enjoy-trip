package enjoytrip.article.service;

import enjoytrip.article.domain.Article;
import enjoytrip.article.dto.*;
import enjoytrip.article.exception.ArticleNotFoundException;
import enjoytrip.article.repository.ArticleRepository;
import enjoytrip.article.util.RequestList;
import enjoytrip.article.util.file.FileStore;
import enjoytrip.article.util.file.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final FileStore fileStore;

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public Page<ArticleFindResponse> find(ArticleFindRequest articleFindRequest,
        Pageable pageable) {
        RequestList<?> requestList = RequestList.builder()
            .data(articleFindRequest)
            .pageable(pageable)
            .build();
        int total = articleRepository.count(articleFindRequest);
        List<Article> content = articleRepository.find(requestList);

        List<ArticleFindResponse> content2 = new ArrayList<>();
        for (Article article : content) {
            content2.add(new ArticleFindResponse(article));
        }
        return new PageImpl<>(content2, pageable, total);
    }

    @Override
    public ArticleFindResponse findById(Long id) {
        Article findArticle = articleRepository.findById(id)
            .orElseThrow(ArticleNotFoundException::new);
        return new ArticleFindResponse(findArticle);
    }

    @Override
    public ArticleSaveResponse save(ArticleSaveRequest articleSaveRequest) throws IOException {

        // imageUUID 생성, 이미지 저장.
        UploadFile uploadFile = fileStore.storeFile(articleSaveRequest.getUploadImage());
        // uploadFile이 null이면 default image 넣어주자.
        if (uploadFile == null) {
            String NO_IMAGE = "no_image.png"; // file.dir 안에 있어야 한다.
            uploadFile = new UploadFile(NO_IMAGE, NO_IMAGE);
        }

        // 시간 입력
        articleSaveRequest.setCreatedAt(LocalDateTime.now());

        Article newArticle = Article.builder()
            .memberId(articleSaveRequest.getMemberId())
            .title(articleSaveRequest.getTitle())
            .content(articleSaveRequest.getContent())
            .imageName(uploadFile.getUploadFileName())
            .imageUUID(uploadFile.getUploadFileUUID())
            .type(articleSaveRequest.getType())
            .views(0)
            .address(articleSaveRequest.getAddress())
            .createdAt(articleSaveRequest.getCreatedAt())
            .createdBy(articleSaveRequest.getMemberId()
                .toString()) // memberRepository에서 id로 email 찾아와서 넣어주자.
            .build();

        Long result = articleRepository.save(newArticle);
        return new ArticleSaveResponse(newArticle);
    }

    @Override
    public ArticleUpdateResponse update(ArticleUpdateRequest articleUpdateRequest,
        MultipartFile updateImage) throws IOException {

        articleRepository.findById(articleUpdateRequest.getId())
            .orElseThrow(ArticleNotFoundException::new);

        // update할 이미지가 있다면 기존 이미지 삭제(no_image.png 제외)
        // 새로운 이미지에 대한 UUID 만들고 저장
        if (updateImage != null) {
            if (!"no_image.png".equals(articleUpdateRequest.getImageUUID())) {
                fileStore.deleteFile(articleUpdateRequest.getImageUUID());
            }
            UploadFile uploadFile = fileStore.storeFile(updateImage);
            articleUpdateRequest.setImageName(uploadFile.getUploadFileName());
            articleUpdateRequest.setImageUUID(uploadFile.getUploadFileUUID());
        }
        // updateAt
        articleUpdateRequest.setUpdatedAt(LocalDateTime.now());
        // updateBy
        articleUpdateRequest.setUpdatedBy(""); // memberId로 email 받아서 넣자.

        Article newArticle = Article.builder()
            .id(articleUpdateRequest.getId())
            .memberId(articleUpdateRequest.getMemberId())
            .title(articleUpdateRequest.getTitle())
            .content(articleUpdateRequest.getContent())
            .imageName(articleUpdateRequest.getImageName())
            .imageUUID(articleUpdateRequest.getImageUUID())
            .views(articleUpdateRequest.getViews())
            .address(articleUpdateRequest.getAddress())
            .type(articleUpdateRequest.getType())
            .updatedAt(articleUpdateRequest.getUpdatedAt())
            .updatedBy(articleUpdateRequest.getUpdatedBy())
            .build();
        Long result = articleRepository.update(newArticle);
        return new ArticleUpdateResponse(newArticle);
    }

    @Override
    public void delete(Long id) {
        String imageUUID = findById(id).getImageUUID();
        if (!"no_image.png".equals(imageUUID)) {
            fileStore.deleteFile(imageUUID);
        }
        articleRepository.delete(id);
    }
}
