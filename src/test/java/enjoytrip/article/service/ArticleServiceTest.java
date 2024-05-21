package enjoytrip.article.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import enjoytrip.article.domain.Article;
import enjoytrip.article.domain.ArticleType;
import enjoytrip.article.dto.request.ArticleSaveRequest;
import enjoytrip.article.dto.request.ArticleUpdateRequest;
import enjoytrip.article.dto.response.ArticleFindResponse;
import enjoytrip.article.repository.ArticleRepository;
import enjoytrip.global.image.FileStore;
import enjoytrip.global.image.Service.ImageService;
import enjoytrip.global.image.UploadFile;
import enjoytrip.global.image.dto.request.Base64Image;
import enjoytrip.member.domain.Member;
import enjoytrip.member.repository.MemberRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FileStore fileStore;

    @DisplayName("게시물 다건 조회 성공")
    @Test
    void findSuccessTest() {
        // given
        ArticleType articleType = ArticleType.BOARD;
        String title = "";
        Pageable pageable = PageRequest.of(0, 5);

        List<Article> content = getArticles();

        doReturn(content).when(articleRepository).findByPage(articleType, title, pageable);
        doReturn(10).when(articleRepository).count(articleType, title);

        // when
        Page<ArticleFindResponse> response = articleService.findByPage(articleType, title,
            pageable);

        // then
        Assertions.assertThat(response.getContent().size()).isEqualTo(content.size());
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
        Assertions.assertThat(response.getId()).isEqualTo(1L);
    }

    @DisplayName("게시물 등록 성공")
    @Test
    void save() throws Exception {

        // given
        UploadFile uploadFile = new UploadFile("fileName", "uuid");
        ArticleSaveRequest request = getArticleSaveRequest();
        String directoryUUID = UUID.randomUUID().toString();
        Member member = getMember();

        doReturn(Optional.ofNullable(member)).when(memberRepository).findById(1L);
//        doReturn(uploadFile).when(fileStore).storeFile(any(MultipartFile.class), any(String.class));
        doReturn(1L).when(articleRepository).save(any(Article.class));

        // when
        articleService.save(request);

        // then
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @DisplayName("게시물 수정 성공")
    @Test
    void update() throws Exception {
        // given
        ArticleUpdateRequest request = getArticleFindRequest();
        Member member = getMember();

        doReturn(1L).when(articleRepository).update(any(Article.class));
        doReturn(Optional.ofNullable(member)).when(memberRepository).findById(1L);

        // when
        articleService.update(request);

        // then
        verify(articleRepository, times(1)).update(any(Article.class));
    }

    @DisplayName("게시물 삭제 성공")
    @Test
    void deleteSuccessTest() throws IOException {
        // given
        doReturn(Optional.ofNullable(getArticle(1L))).when(articleRepository)
            .findById(any(Long.class));
        // when
        articleService.delete(1L);
        // then
        verify(articleRepository, times(1)).delete(1L);

    }

    private static ArticleUpdateRequest getArticleFindRequest() {
        return ArticleUpdateRequest.builder()
            .id(1L)
            .memberId(1L)
            .title("title")
            .content("content")
            .selectedImages(new ArrayList<>())
            .notSelectedImages(new ArrayList<>())
            .directoryUUID(UUID.randomUUID().toString())
            .views(0)
            .address("address")
            .articleType(ArticleType.TRIP)
            .build();
    }

    private static List<Article> getArticles() {
        List<Article> content = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            content.add(getArticle(i));
        }
        return content;
    }

    private static Member getMember() {
        return Member.builder()
            .id(1L)
            .name("name")
            .build();
    }
    private static ArticleSaveRequest getArticleSaveRequest() {

        return ArticleSaveRequest.builder()
            .memberId(1L)
            .title("title")
            .content("content")
            .notSelectedImages(new ArrayList<>())
            .selectedImages(new ArrayList<>())
            .directoryUUID(UUID.randomUUID().toString())
            .address("address")
            .articleType(ArticleType.TRIP)
            .build();
    }

    private static Base64Image getBase64Image() {
        return Base64Image.builder()
            .originalName("originalName")
            .base64File("base64File")
            .extension("extension")
            .build();
    }

    private static Article getArticle(long id) {
        return Article.builder()
            .id(id)
            .memberId(1L)
            .title("title")
            .content("content")
            .views(1)
            .address("address")
            .articleType(ArticleType.BOARD)
            .createdAt(LocalDateTime.now())
            .createdBy("createdBy")
            .updatedAt(LocalDateTime.now())
            .updatedBy("updatedBy")
            .build();
    }

}