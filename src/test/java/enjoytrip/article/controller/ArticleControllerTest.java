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
import enjoytrip.article.domain.ArticleType;
import enjoytrip.global.image.dto.request.Base64Image;
import enjoytrip.article.dto.request.ArticleSaveRequest;
import enjoytrip.article.dto.request.ArticleUpdateRequest;
import enjoytrip.article.dto.response.ArticleSaveResponse;
import enjoytrip.article.dto.response.ArticleUpdateResponse;
import enjoytrip.article.service.ArticleService;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("게시물 다건 조회 성공")
    @Test
    void findByPageSuccessTest() throws Exception {
        //expected
        mockMvc.perform(get("/articles")
                .param("articleType", "TRIP")
                .param("title", "")
                .param("page", "0")
                .param("size", "10").characterEncoding("UTF-8"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("게시물 단건 조회 성공")
    @Test
    void findByIdSuccessTest() throws Exception {
        // expected
        mockMvc.perform(get("/articles/{id}", 1L))
            .andExpect(status().isOk())
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

        doReturn(response).when(articleService)
            .save(any(ArticleSaveRequest.class));

        // when & exptected
        mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(Charset.defaultCharset())
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

        String requestJson = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(request);

        String responseJson = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(response);

        doReturn(response).when(articleService)
            .update(any(ArticleUpdateRequest.class));

        // expected
        mockMvc.perform(
                put("/articles/{id}", 1L)
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson))
            .andDo(print());

    }

    @DisplayName("게시물 삭제 성공")
    @Test
    void deleteSuccessTest() throws Exception {
        mockMvc.perform(delete("/articles/{id}", 1L))
            .andExpect(status().isNoContent());
    }

    private static ArticleUpdateRequest getArticleUpdateRequest() {
        return ArticleUpdateRequest.builder()
            .id(1L)
            .memberId(1L)
            .title("title")
            .content("content")
            .selectedImages(new ArrayList<>())
            .notSelectedImages(new ArrayList<>())
            .directoryUUID(UUID.randomUUID().toString())
            .views(0)
            .address("address").articleType(ArticleType.BOARD)
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
            .extension("extension").build();
    }
}