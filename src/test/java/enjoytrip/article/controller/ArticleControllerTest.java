package enjoytrip.article.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import enjoytrip.article.domain.Article;
import enjoytrip.article.domain.ArticleType;
import enjoytrip.article.dto.Base64Image;
import enjoytrip.article.dto.form.ArticleSaveForm;
import enjoytrip.article.dto.form.ArticleUpdateForm;
import enjoytrip.article.dto.request.ArticleFindRequest;
import enjoytrip.article.dto.request.ArticleSaveRequest;
import enjoytrip.article.dto.request.ArticleUpdateRequest;
import enjoytrip.article.dto.response.ArticleFindResponse;
import enjoytrip.article.dto.response.ArticleSaveResponse;
import enjoytrip.article.dto.response.ArticleUpdateResponse;
import enjoytrip.article.service.ArticleService;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

    @InjectMocks
    private ArticleController articleController;

    @Mock
    private ArticleService articleService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();

        objectMapper = new ObjectMapper();
    }

    @DisplayName("게시물 다건 조회 성공")
    @Test
    void findByPageSuccessTest() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        ArticleFindRequest request = new ArticleFindRequest(ArticleType.BOARD, "title");

        List<ArticleFindResponse> list = getArticleFindResponses();
        Page<ArticleFindResponse> response = new PageImpl<>(list, pageable, 0);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(response);

        doReturn(response).when(articleService)
            .findByPage(any(ArticleFindRequest.class), any(Pageable.class));

        // expected
        mockMvc.perform(get("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "10")
                .content(requestJson)
                .characterEncoding("UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson))
            .andDo(print());
    }

    @DisplayName("게시물 단건 조회 성공")
    @Test
    void findByIdSuccessTest() throws Exception {
        // given
        ArticleFindResponse response = getArticleFindResponse();

        String responseJson = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(response);

        doReturn(response).when(articleService).findById(1L);

        // expected

        mockMvc.perform(get("/articles/{id}", 1L))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson))
            .andDo(print());

    }

    @DisplayName("게시물 등록 성공")
    @Test
    void saveSuccessTest() throws Exception {
        //given
        ArticleSaveRequest request = getArticleSaveRequest();
        ArticleSaveResponse response = new ArticleSaveResponse(1L);

        String requestJson = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(request);
        String responseJson = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(response);

        doReturn(response).when(articleService).save(any(ArticleSaveForm.class));

        // exptected
        mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isCreated())
            .andExpect(content().json(responseJson))
            .andDo(print());
    }

    @DisplayName("게시물 수정 성공")
    @Test
    void updateSuccessTest() throws Exception {
        // given
        ArticleUpdateRequest request = getArticleUpdateRequest();
        ArticleUpdateResponse response = new ArticleUpdateResponse(1L);
        ArticleUpdateForm updateForm = new ArticleUpdateForm(request);

        String requestJson = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(request);

//        String responseJson = objectMapper.registerModule(new JavaTimeModule())
//            .writeValueAsString(response);

        doReturn(response).when(articleService)
            .update(any(ArticleUpdateForm.class));

        // expected
        mockMvc.perform(put("/articles/{id}", 1L)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
            )
            .andExpect(status().isOk())
            .andDo(print());

    }

    @DisplayName("게시물 삭제 성공")
    @Test
    void deleteSuccessTest() throws Exception {
        mockMvc.perform(delete("/articles/{id}", 1L)
        ).andExpect(status().isNoContent());
    }

    private static List<ArticleFindResponse> getArticleFindResponses() {
        List<ArticleFindResponse> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(new ArticleFindResponse(Article.builder()
                .id((long) i)
                .build()));
        }
        return list;
    }

    private static ArticleUpdateRequest getArticleUpdateRequest() {
        return ArticleUpdateRequest.builder()
            .id(1L)
            .memberId(1L)
            .title("title")
            .content("content")
            .imageName("imageName")
            .imageUUID("imageUUID")
            .base64Image(getBase64Image())
            .views(0)
            .address("address")
            .type(ArticleType.BOARD)
            .build();
    }

    private static ArticleSaveRequest getArticleSaveRequest() {

        return ArticleSaveRequest.builder()
            .memberId(1L)
            .title("title")
            .content("content")
            .base64Image(getBase64Image())
            .address("address")
            .type(ArticleType.BOARD)
            .build();
    }

    private static Base64Image getBase64Image() {
        return Base64Image.builder()
            .originalName("originalName")
            .base64File("base64File")
            .extension("extension")
            .build();
    }

    private static ArticleFindResponse getArticleFindResponse() {
        return new ArticleFindResponse(Article.builder()
            .id(1L)
            .memberId(1L)
            .title("title")
            .content("content")
            .imageName("imageName")
            .imageUUID(UUID.randomUUID().toString())
            .views(0)
            .address("address")
            .type(ArticleType.BOARD)
            .createdAt(LocalDateTime.now())
            .createdBy("createdBy")
            .updatedAt(LocalDateTime.now())
            .updatedBy("updatedBy")
            .build());
    }
}