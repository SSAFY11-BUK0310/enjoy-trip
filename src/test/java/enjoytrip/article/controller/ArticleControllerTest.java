package enjoytrip.article.controller;

import ch.qos.logback.core.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import enjoytrip.article.domain.Article;
import enjoytrip.article.dto.*;
import enjoytrip.article.service.ArticleService;
import enjoytrip.article.util.file.FileStore;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.SerializationUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xmlunit.builder.Input;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

    @InjectMocks
    private ArticleController articleController;

    @Mock
    private ArticleService articleService;

    @Mock
    private FileStore fileStore;

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
    void findSuccessTest() throws Exception {
        // given
//        ArticleFindRequest articleFindRequest = new ArticleFindRequest("tour", null);
        Pageable pageable = PageRequest.of(0, 10);

        List<ArticleFindResponse> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(new ArticleFindResponse(Article.builder()
                    .id((long) i)
                    .memberId((long) 1)
                    .title("title" + i)
                    .content("content" + i)
                    .imageName("imageName" + i)
                    .imageUUID("imageUUID" + i)
                    .address("address" + i)
                    .type("type" + i)
                    .createdAt(LocalDateTime.now())
                    .createdBy("createdBy" + i)
                    .updatedAt(LocalDateTime.now())
                    .updatedBy("updatedBy" + i)
                    .build()));
        }

        Page<ArticleFindResponse> response = new PageImpl<>(list, pageable, 0);

        String responseJson = objectMapper.registerModule(new JavaTimeModule())
                .writeValueAsString(response);

        doReturn(response).when(articleService).find(any(ArticleFindRequest.class), any(Pageable.class));

        // expected
        mockMvc.perform(get("/articles")
                        .param("type", "tour")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());


    }

    @DisplayName("게시물 단건 조회 성공")
    @Test
    void findByIdSuccessTest() throws Exception {
        // given
        ArticleFindResponse response = new ArticleFindResponse(Article.builder()
                .id(1L)
                .memberId(1L)
                .title("title")
                .content("content")
                .imageName("imageName")
                .imageUUID("imageUUID")
                .address("address")
                .type("type")
                .createdAt(LocalDateTime.now())
                .createdBy("createdBy")
                .updatedAt(LocalDateTime.now())
                .updatedBy("updatedBy")
                .build());

        String responseJson = objectMapper.registerModule(new JavaTimeModule())
                .writeValueAsString(response);

        doReturn(response).when(articleService).findById(1L);

        // expected

        mockMvc.perform(get("/articles/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());

    }


    @Test
    void saveSuccessTest() throws Exception {
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
//                .uploadImage(uploadImage)
                .address("address")
                .type("type")
                .createdAt(LocalDateTime.now())
                .build();

        ArticleSaveResponse response = new ArticleSaveResponse(Article.builder()
                .id(1L)
                .memberId(1L)
                .title("title")
                .content("content")
                .imageName("imageName")
                .imageUUID("imageUUID")
                .address("address")
                .type("type")
                .createdAt(LocalDateTime.now())
                .createdBy("createdBy")
                .updatedAt(LocalDateTime.now())
                .updatedBy("updatedBy")
                .build());

        String requestJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request);
        String responseJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(response);

        doReturn(response).when(articleService).save(any(ArticleSaveRequest.class));

        /*
          - 해결 못한 부분 설명
          인터넷에 찾아보면 거의 모두 MockMultipartFile을 만들고 아래처럼 file(mockFile)을 집어 넣는다.
          근데 print() 결과에 보면 file에 대한 내용이 없다.
         */
        // exptected
        mockMvc.perform(multipart("/articles")
                        .file(uploadImage)
                        .content(requestJson)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding(Charset.defaultCharset()))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());

    }

    @Test
    void update() throws Exception{
        // given
        MockMultipartFile updateImage = new MockMultipartFile(
                "updateImage",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/images/test.png")
        );

        ArticleUpdateRequest request = ArticleUpdateRequest.builder()
                .id(1L)
                .memberId(1L)
                .title("title")
                .content("content")
                .imageName("imageName")
                .imageUUID("imageUUID")
                .address("address")
                .type("type")
                .updatedAt(LocalDateTime.now())
                .updatedBy("updatedBy")
                .build();

        String requestJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request);
        MockMultipartFile requestFile = new MockMultipartFile(
                "data",
                "",
                String.valueOf(MediaType.APPLICATION_JSON),
                requestJson.getBytes(StandardCharsets.UTF_8)
        );

        ArticleUpdateResponse response = new ArticleUpdateResponse(
                Article.builder()
                        .id(1L)
                        .memberId(1L)
                        .title("title")
                        .content("content")
                        .imageName("imageName")
                        .imageUUID("imageUUID")
                        .address("address")
                        .type("type")
                        .createdAt(LocalDateTime.now())
                        .createdBy("createdBy")
                        .updatedAt(LocalDateTime.now())
                        .updatedBy("updatedBy")
                        .build()
        );

        doReturn(response).when(articleService).update(any(ArticleUpdateRequest.class), eq(updateImage));

        mockMvc.perform(multipart(HttpMethod.PUT, "/articles/{id}", 1L)
                        .file(updateImage)
                        .file(requestFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
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
}