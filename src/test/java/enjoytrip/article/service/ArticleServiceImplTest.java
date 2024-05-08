package enjoytrip.article.service;

import enjoytrip.article.domain.Article;
import enjoytrip.article.dto.*;
import enjoytrip.article.repository.ArticleRepository;
import enjoytrip.article.util.RequestList;
import enjoytrip.article.util.file.FileStore;
import enjoytrip.article.util.file.UploadFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private FileStore fileStore;

    @BeforeEach
    public void init() {

    }

    @DisplayName("게시물 단건 조회 성공")
    @Test
    void findByIdSuccessTest() {
        // given
        Article article = getArticle(1L);

        doReturn(Optional.ofNullable(article)).when(articleRepository).findById(1L);

        // when
        ArticleFindResponse response = articleService.findById(1L);

        // then
        Assertions.assertThat(response.getId()).isEqualTo(new ArticleFindResponse(article).getId());
    }

    @DisplayName("게시물 다건 조회 성공")
    @Test
    void findSuccessTest() {
        // given
        ArticleFindRequest articleFindRequest = new ArticleFindRequest("tour", null);
        Pageable pageable = PageRequest.of(0, 5);
        List<Article> content = new ArrayList<>();

        for (int i = 1; i <= 10; i ++) {
            content.add(getArticle((long) i));
        }

        doReturn(content).when(articleRepository).find(any(RequestList.class));
        doReturn(content.size()).when(articleRepository).count(any(ArticleFindRequest.class));

        // when
        Page<ArticleFindResponse> response = articleService.find(articleFindRequest, pageable);

        // then
        Assertions.assertThat(response.getTotalElements()).isEqualTo(content.size());
    }

    @Test
    void save() throws IOException {

        // given
        MockMultipartFile uploadImage = new MockMultipartFile(
                "uploadImage",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/images/test.png")
        );
        ArticleSaveRequest request = ArticleSaveRequest.builder()
                .memberId(1L)
                .title("title")
                .content("content")
                .uploadImage(uploadImage)
                .address("address")
                .type("tour")
                .createdAt(LocalDateTime.now())
                .build();

        UploadFile uploadFile = new UploadFile("fileName", "uuid");
        doReturn(uploadFile).when(fileStore).storeFile(uploadImage);
        doReturn(1L).when(articleRepository).save(any(Article.class));

        // when
        ArticleSaveResponse response = articleService.save(request);

        // then
        Assertions.assertThat(response.getImageName()).isEqualTo(uploadFile.getUploadFileName());

    }

    @Test
    void update() throws IOException{
        // given
        MockMultipartFile updateImage = new MockMultipartFile(
                "updateImage",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/images/test.png")
        );
        ArticleUpdateRequest articleUpdateRequest = ArticleUpdateRequest.builder()
                .id(1L)
                .memberId(1L)
                .title("title")
                .content("content")
                .imageName("imageName")
                .imageUUID("imageUUID")
                .build();

        doReturn(1L).when(articleRepository).update(any(Article.class));
        doReturn(new UploadFile("fileName", "uuid")).when(fileStore).storeFile(any(MultipartFile.class));
        doReturn(Optional.ofNullable(getArticle(1L))).when(articleRepository).findById(any(Long.class));

        // when
        ArticleUpdateResponse response = articleService.update(articleUpdateRequest , updateImage);

        // then
        Assertions.assertThat(response.getImageName()).isEqualTo("fileName");
    }

    @Test
    void delete() {
        // given
        Article article = getArticle(1L);
        doReturn(Optional.ofNullable(article)).when(articleRepository).findById(1L);

        // when
        articleService.delete(1L);

        // then
        verify(articleRepository, times(1)).delete(1L);
    }

    private static Article getArticle(long id) {
        return Article.builder()
                .id(id)
                .memberId(1L)
                .title("title")
                .content("content")
                .imageName("imageName")
                .imageUUID("imageUUID")
                .address("address")
                .views(1)
                .type("tour")
                .createdAt(LocalDateTime.now())
                .createdBy("createdBy")
                .updatedAt(LocalDateTime.now())
                .updatedBy("updatedBy")
                .build();
    }
}