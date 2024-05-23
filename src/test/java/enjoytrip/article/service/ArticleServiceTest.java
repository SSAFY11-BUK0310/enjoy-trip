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
import enjoytrip.global.image.service.ImageService;
import enjoytrip.global.image.dto.request.Base64Image;
import enjoytrip.like.service.LikeService;
import enjoytrip.member.domain.Member;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.service.MemberService;
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
import org.springframework.boot.test.mock.mockito.MockBean;
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
    private LikeService likeService;

    @Mock
    private MemberService memberService;


    @DisplayName("게시물 다건 조회 성공")
    @Test
    void findSuccessTest() {
        // given
        ArticleType articleType = ArticleType.BOARD;
        String title = "";
        Pageable pageable = PageRequest.of(0, 5);
        Member member = getMember();
        MemberFindResponse memberFindResponse = new MemberFindResponse(member);
        List<Article> content = getArticles();

        doReturn(content).when(articleRepository).findByPage(articleType, title, pageable);
        doReturn(10).when(articleRepository).count(articleType, title);
        doReturn(memberFindResponse).when(memberService).findById(1L);
        doReturn(1L).when(likeService).countByArticleId(1L);
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
        MemberFindResponse memberFindResponse = new MemberFindResponse(getMember());
        doReturn(memberFindResponse).when(memberService).findById(1L);
        doReturn(1L).when(likeService).countByArticleId(1L);
        // when
        ArticleFindResponse response = articleService.findById(1L);

        // then
        Assertions.assertThat(response.getId()).isEqualTo(1L);
    }

    @DisplayName("게시물 등록 성공")
    @Test
    void save() throws Exception {

        // given
        ArticleSaveRequest request = getArticleSaveRequest();
        Member member = getMember();
        MemberFindResponse memberFindResponse = new MemberFindResponse(member);
        doReturn(memberFindResponse).when(memberService).findById(1L);
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
        MemberFindResponse memberFindResponse = new MemberFindResponse(member);

        doReturn(1L).when(articleRepository).update(any(Article.class));
        doReturn(memberFindResponse).when(memberService).findById(1L);

        // when
        articleService.update(request);

        // then
        verify(articleRepository, times(1)).update(any(Article.class));
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

    private Member getMember() {
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
            .directoryUUID(UUID.randomUUID().toString())
            .build();
    }
}