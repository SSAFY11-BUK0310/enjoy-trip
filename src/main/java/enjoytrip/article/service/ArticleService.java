package enjoytrip.article.service;

import static enjoytrip.global.exception.ErrorCode.ARTICLE_NOT_FOUND;
import static enjoytrip.global.exception.ErrorCode.ARTICLE_SAVE_FAILED;
import static enjoytrip.global.exception.ErrorCode.ARTICLE_UPDATE_FAILED;
import static enjoytrip.global.exception.ErrorCode.MEMBER_NOT_FOUND;

import enjoytrip.article.domain.Article;
import enjoytrip.article.domain.ArticleType;
import enjoytrip.article.dto.request.ArticleSaveRequest;
import enjoytrip.article.dto.request.ArticleUpdateRequest;
import enjoytrip.article.dto.response.ArticleFindResponse;
import enjoytrip.article.dto.response.ArticleSaveResponse;
import enjoytrip.article.dto.response.ArticleUpdateResponse;
import enjoytrip.article.exception.ArticleNotFoundException;
import enjoytrip.article.exception.ArticleSaveFailedException;
import enjoytrip.article.exception.ArticleUpdateFailedException;
import enjoytrip.article.repository.ArticleRepository;
import enjoytrip.global.image.FileStore;
import enjoytrip.global.image.Service.ImageService;
import enjoytrip.global.image.domain.Image;
import enjoytrip.member.domain.Member;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.exception.MemberNotFoundException;
import enjoytrip.member.repository.MemberRepository;
import enjoytrip.member.service.MemberService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ImageService imageService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    @Transactional
    public ArticleSaveResponse save(ArticleSaveRequest request) throws Exception {

        ArticleSaveResponse response = new ArticleSaveResponse();
        MemberFindResponse findMember = memberService.findById(request.getMemberId());

        // article 테이블에 등록
        LocalDateTime now = LocalDateTime.now();
        Article newArticle = Article.builder()
            .memberId(request.getMemberId())
            .title(request.getTitle())
            .content(request.getContent())
            .articleType(request.getArticleType())
            .views(0)
            .address(request.getAddress())
            .directoryUUID(request.getDirectoryUUID())
            .createdAt(now)
            .createdBy(findMember.getEmail())
            .updatedAt(now)
            .createdBy(findMember.getEmail())
            .build();

        Long articleSaveResult = articleRepository.save(newArticle);
        response.addId(newArticle.getId());
        if (articleSaveResult == 0) {
            throw new ArticleSaveFailedException(ARTICLE_SAVE_FAILED, "게시물 등록 실패");
        }

        // article 테이블에 등록 완료, 게시물에 사용되는 이미지들을 image 테이블에 추가
        List<String> selectedImages = request.getSelectedImages();
        String directoryUUID = request.getDirectoryUUID();
        for (String selectedImage : selectedImages) {
            MultipartFile multipartFile = fileStore.retrieveFile(directoryUUID, selectedImage);

            Image newImage = Image.builder()
                .articleId(response.getId())
                .imageName(multipartFile.getOriginalFilename())
                .imageUUID(selectedImage)
                .directoryUUID(directoryUUID)
                .createdAt(now)
                .createdBy(findMember.getEmail())
                .updatedAt(now)
                .updatedBy(findMember.getEmail())
                .build();

            imageService.saveToDB(newImage);
        }

        // 게시물에 사용되지 않는 이미지들은 image 저장소에서 제거해야 한다.
        List<String> notSelectedImages = request.getNotSelectedImages();
        for (String notSelectedImage : notSelectedImages) {
            fileStore.deleteFile(directoryUUID, notSelectedImage);
        }
        return response;
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
        ArticleFindResponse response = new ArticleFindResponse(findArticle);
        addExtraData(findArticle, response);
        return response;
    }

    @Transactional
    public ArticleUpdateResponse update(ArticleUpdateRequest request) throws Exception {
        ArticleUpdateResponse response = new ArticleUpdateResponse();
        MemberFindResponse findMember = memberService.findById(request.getMemberId());
        // 게시물 갱신
        LocalDateTime now = LocalDateTime.now();
        Article newArticle = Article.builder()
            .id(request.getId())
            .memberId(request.getMemberId())
            .title(request.getTitle())
            .content(request.getContent())
            .views(request.getViews())
            .address(request.getAddress())
            .articleType(request.getArticleType())
            .directoryUUID(request.getDirectoryUUID())
            .updatedAt(now)
            .updatedBy(findMember.getEmail())
            .build();

        Long articleUpdateResult = articleRepository.update(newArticle);
        response.addId(newArticle.getId());
        if (articleUpdateResult == 0) {
            throw new ArticleUpdateFailedException(ARTICLE_UPDATE_FAILED, "게시물 수정 실패");
        }

        String directoryUUID = request.getDirectoryUUID();

        // 새롭게 추가된 이미지 등록
        List<String> selectedImages = request.getSelectedImages();
        List<Image> imagesDB = imageService.findByArticleId(request.getId());
        for (String selectedImage : selectedImages) {
            MultipartFile multipartFile = fileStore.retrieveFile(directoryUUID, selectedImage);

            Image newImage = Image.builder()
                .articleId(request.getId())
                .imageName(multipartFile.getOriginalFilename())
                .imageUUID(selectedImage)
                .directoryUUID(directoryUUID)
                .createdAt(now)
                .createdBy(findMember.getEmail())
                .updatedAt(now)
                .updatedBy(findMember.getEmail())
                .build();
            imageService.saveToDB(newImage);

        }
        // 기존의 이미지를 사용하지 않는 경우, DB와 이미지 저장소에서 제거해야 한다.
        String content = request.getContent();
        for (Image image : imagesDB) {
            if (!content.contains(image.getImageUUID())) {
                fileStore.deleteFile(directoryUUID, image.getImageUUID());
                imageService.deleteByImageUUID(image.getImageUUID());
            }
        }

        // 사용하지 않는 이미지 제거
        List<String> notSelectedImages = request.getNotSelectedImages();
        for (String notSelectedImage : notSelectedImages) {
            fileStore.deleteFile(directoryUUID, notSelectedImage);
        }
        return response;
    }

    public void delete(Long id) throws IOException {
        ArticleFindResponse article = findById(id);
        fileStore.rmdir(article.getDirectoryUUID());
        imageService.deleteByArticleId(id);
        articleRepository.delete(id);
    }

    public void updateViews(Long id) {
        articleRepository.updateViews(id);
    }

    private List<ArticleFindResponse> getArticleFindResponseList(List<Article> content) {
        List<ArticleFindResponse> articleFindResponseList = new ArrayList<>();
        for (Article article : content) {
            ArticleFindResponse articleFindResponse = new ArticleFindResponse(article);
            addExtraData(article, articleFindResponse);
            articleFindResponseList.add(articleFindResponse);
        }
        return articleFindResponseList;
    }

    public void addExtraData(Article article, ArticleFindResponse articleFindResponse) {
        List<Image> images = imageService.findByArticleId(article.getId());
        articleFindResponse.addImages(images);
        MemberFindResponse findMember = memberService.findById(article.getMemberId());
        articleFindResponse.addMemberName(findMember.getName());
    }
}
