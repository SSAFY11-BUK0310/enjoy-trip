package enjoytrip.article.service;

import static enjoytrip.global.exception.ErrorCode.ARTICLE_NOT_FOUND;
import static enjoytrip.global.exception.ErrorCode.MEMBER_NOT_FOUND;

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
import enjoytrip.global.image.FileStore;
import enjoytrip.global.image.Service.ImageService;
import enjoytrip.global.image.UploadFile;
import enjoytrip.global.image.domain.Image;
import enjoytrip.member.domain.Member;
import enjoytrip.member.exception.MemberNotFoundException;
import enjoytrip.member.repository.MemberRepository;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    public static final String NO_IMAGE_PNG = "no_image.png";
    private final ArticleRepository articleRepository;
    private final ImageService imageService;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    public ArticleSaveResponse save(ArticleSaveRequest request) throws Exception {
        List<String> uploadedImageNames = new ArrayList<>();
        ArticleSaveResponse response = new ArticleSaveResponse();
        try {
            Member findMember = memberRepository.findById(request.getMemberId()).orElseThrow(
                () -> new MemberNotFoundException(MEMBER_NOT_FOUND, "does not exist member"));

            LocalDateTime now = LocalDateTime.now();
            Article newArticle = Article.builder()
                .memberId(request.getMemberId())
                .title(request.getTitle())
                .content(request.getContent())
                .articleType(request.getArticleType())
                .views(0)
                .address(request.getAddress())
                .createdAt(now)
                .createdBy(findMember.getName())
                .updatedAt(now)
                .createdBy(findMember.getName())
                .build();

            Long articleSaveResult = articleRepository.save(newArticle);
            response.addId(newArticle.getId());
            if (articleSaveResult == 0 || response.getId() == null) {
                // Todo: 예외 발생 시켜야한다. 게시물이 등록되지 않았다.
                throw new RuntimeException();
            }

            // 게시물 등록까지 완료, 이미지에 대한 등록 실시한다.
            List<MultipartFile> multipartFiles = getMultipartFiles(request.getBase64Images());

            List<UploadFile> uploadFiles = new ArrayList<>();
            for (MultipartFile multipartFile : multipartFiles) {
                UploadFile uploadFile = fileStore.storeFile(multipartFile);
                if (uploadFile != null) {
                    uploadedImageNames.add(uploadFile.getUploadFileUUID());
                    uploadFiles.add(uploadFile);
                }
            }

            if (uploadFiles.isEmpty()) {
                uploadFiles.add(new UploadFile(NO_IMAGE_PNG, NO_IMAGE_PNG));
            }

            for (UploadFile uploadFile : uploadFiles) {
                Image articleImage = Image.builder()
                    .articleId(newArticle.getId())
                    .imageName(uploadFile.getUploadFileName())
                    .imageUUID(uploadFile.getUploadFileUUID())
                    .createdAt(now)
                    .createdBy(findMember.getName())
                    .updatedAt(now)
                    .createdBy(findMember.getName())
                    .build();
                Long articleImageSaveResult = imageService.save(articleImage);

                if (articleImageSaveResult == 0) {
                    throw new RuntimeException();
                }
            }
        } catch (Exception e) {
            if (!uploadedImageNames.isEmpty()) {
                for (String imageName : uploadedImageNames) {
                    if (!NO_IMAGE_PNG.equals(imageName)) {
                        fileStore.deleteFile(imageName);
                    }
                }
            }
            throw e;
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
        return new ArticleFindResponse(findArticle);
    }

    public ArticleUpdateResponse update(ArticleUpdateRequest request) throws Exception {
        List<String> uploadedImageNames = new ArrayList<>();
        List<MockMultipartFile> deletedFiles = new ArrayList<>();
        ArticleUpdateResponse response = new ArticleUpdateResponse();
        try {
            Member findMember = memberRepository.findById(request.getMemberId()).orElseThrow(
                () -> new MemberNotFoundException(MEMBER_NOT_FOUND, "does not exist member"));

            LocalDateTime now = LocalDateTime.now();
            Article newArticle = Article.builder()
                .id(request.getId())
                .memberId(request.getMemberId())
                .title(request.getTitle())
                .content(request.getContent())
                .views(request.getViews())
                .address(request.getAddress())
                .articleType(request.getArticleType())
                .updatedAt(now)
                .updatedBy(findMember.getName())
                .build();

            Long articleUpdateResult = articleRepository.update(newArticle);
            response.addId(newArticle.getId());
            if (articleUpdateResult == 0 || response.getId() == null) {
                throw new RuntimeException();
            }
            // 기존 이미지 삭제 후 등록
            List<Image> articleImages = imageService.findByArticleId(
                newArticle.getId());

            for (Image image : articleImages) {
                fileStore.deleteFile(image.getImageUUID());

                File file = new File(fileStore.getFullPath(image.getImageUUID()));
                FileInputStream fileInputStream = new FileInputStream(file);
                String mimeType = Files.probeContentType(file.toPath());
                deletedFiles.add(new MockMultipartFile("file", file.getName(), mimeType, fileInputStream));
            }

            imageService.deleteByArticleId(newArticle.getId());

            List<MultipartFile> multipartFiles = getMultipartFiles(request.getBase64Images());
            List<UploadFile> uploadFiles = new ArrayList<>();
            for (MultipartFile multipartFile : multipartFiles) {
                UploadFile uploadFile = fileStore.storeFile(multipartFile);
                if (uploadFile != null) {
                    uploadedImageNames.add(uploadFile.getUploadFileUUID());
                    uploadFiles.add(uploadFile);
                }
            }

            for (UploadFile uploadFile : uploadFiles) {
                Image articleImage = Image.builder()
                    .articleId(newArticle.getId())
                    .imageName(uploadFile.getUploadFileName())
                    .imageUUID(uploadFile.getUploadFileUUID())
                    .createdAt(now)
                    .createdBy(findMember.getName())
                    .updatedAt(now)
                    .createdBy(findMember.getName())
                    .build();
                Long articleImageSaveResult = imageService.save(articleImage);
                if (articleImageSaveResult == 0) {
                    throw new RuntimeException();
                }
            }
        } catch (Exception e) {

            for (String imageName : uploadedImageNames) {
                if (!NO_IMAGE_PNG.equals(imageName)) {
                    fileStore.deleteFile(imageName);
                }
            }

            for (MultipartFile multipartFile : deletedFiles) {
                multipartFile.transferTo(new File(fileStore.getFullPath(multipartFile.getOriginalFilename())));
            }
            throw e;
        }
        return response;
    }

    public void delete(Long id) {

        articleRepository.delete(id);
    }

    private List<ArticleFindResponse> getArticleFindResponseList(List<Article> content) {
        List<ArticleFindResponse> articleFindResponseList = new ArrayList<>();
        for (Article article : content) {
            articleFindResponseList.add(new ArticleFindResponse(article));
        }
        return articleFindResponseList;
    }

    private List<MultipartFile> getMultipartFiles(List<Base64Image> base64Images) {
        List<MultipartFile> multipartFilesResult = new ArrayList<>();
        if (base64Images == null) {
            return multipartFilesResult;
        }

        for (Base64Image base64Image : base64Images) {
            multipartFilesResult.add(getMultipartFile(base64Image));
        }

        return multipartFilesResult;
    }

    private MultipartFile getMultipartFile(Base64Image base64Image) {

        String fileName = base64Image.getOriginalName();
        String base64File = base64Image.getBase64File();
        String extension = base64Image.getExtension();

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByte = decoder.decode(base64File.getBytes());

        return new MockMultipartFile("image", fileName, extension, decodedByte);
    }
}
