package enjoytrip.like.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import enjoytrip.config.AbstractRestDocsTest;
import enjoytrip.like.domain.Like;
import enjoytrip.like.dto.request.LikeDeleteRequest;
import enjoytrip.like.dto.request.LikeExistRequest;
import enjoytrip.like.dto.request.LikeSaveRequest;
import enjoytrip.like.dto.response.LikeSaveResponse;
import enjoytrip.like.service.LikeService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(LikeController.class)
class LikeControllerRestDocsTest extends AbstractRestDocsTest {

    @MockBean
    LikeService likeService;

    @Test
    @DisplayName("좋아요 저장 요청 성공")
    void save() throws Exception {
        // given
        LikeSaveRequest request = getLikeSaveRequest();
        LikeSaveResponse response = getLikeSaveResponse();
        String requestJson = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(request);
        String responseJson = objectMapper.writeValueAsString(response);
        doReturn(response).when(likeService).save(any(LikeSaveRequest.class));

        // expected
        mockMvc.perform(post("/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(requestJson)
            )
            .andExpect(status().isCreated())
            .andExpect(content().json(responseJson))
            .andDo(print());
    }

    @Test
    @DisplayName("좋아요 삭제 요청 성공")
    void delete() throws Exception {
        // expected
        LikeDeleteRequest request = getLikeDeleteRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.delete("/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(requestJson)
            )
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @Test
    @DisplayName("사용자 ID로 좋아요 목록 조회 성공")
    void findByMemberId() throws Exception {

        // given
        List<Like> response = getLikeList(10);
        String responseJson = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(response);

        doReturn(response).when(likeService).findByMemberId(1L);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/likes/{memberId}/memberId", 1L)
            )
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson))
            .andDo(print());
    }

    @Test
    @DisplayName("게시물 ID로 좋아요 목록 조회 성공")
    void findByArticleId() throws Exception {
        // given
        List<Like> response = getLikeList(10);
        String responseJson = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(response);

        doReturn(response).when(likeService).findByArticleId(1L);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/likes/{articleId}/articleId", 1L)
            )
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson))
            .andDo(print());
    }

    @Test
    void exists() throws Exception {
        // given
        LikeExistRequest request = getLikeExistRequest();

        doReturn(true).when(likeService).exists(any(LikeExistRequest.class));

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/likes/exists")
                .param("articleId", "1")
                .param("memberId", "1"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    private static LikeExistRequest getLikeExistRequest() {
        return LikeExistRequest.builder()
            .articleId(1L)
            .memberId(1L)
            .build();
    }

    public List<Like> getLikeList(int size) {
        List<Like> response = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            response.add(getLike((long) i));
        }
        return response;
    }

    private static Like getLike(Long id) {
        return Like.builder()
            .id(id)
            .articleId(id)
            .memberId(id)
            .createdAt(LocalDateTime.now())
            .createdBy("dummyEmail")
            .updatedAt(LocalDateTime.now())
            .updatedBy("dummyEmail")
            .build();
    }

    private static LikeDeleteRequest getLikeDeleteRequest() {
        return LikeDeleteRequest.builder()
            .articleId(1L)
            .memberId(1L)
            .build();
    }

    private static LikeSaveResponse getLikeSaveResponse() {
        LikeSaveResponse response = new LikeSaveResponse();
        response.addId(1L);
        return response;
    }

    private static LikeSaveRequest getLikeSaveRequest() {
        return LikeSaveRequest.builder()
            .articleId(1L)
            .memberId(1L)
            .build();
    }

}