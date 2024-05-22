package enjoytrip.comment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import enjoytrip.comment.domain.Comment;
import enjoytrip.comment.dto.request.CommentSaveRequest;
import enjoytrip.comment.dto.request.CommentUpdateRequest;
import enjoytrip.comment.dto.response.CommentFindResponse;
import enjoytrip.comment.dto.response.CommentSaveResponse;
import enjoytrip.comment.dto.response.CommentUpdateResponse;
import enjoytrip.comment.service.CommentService;
import enjoytrip.config.AbstractRestDocsTest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(CommentController.class)
class CommentControllerRestDocsTest extends AbstractRestDocsTest {

  @MockBean
  CommentService commentService;

  @Test
  @DisplayName("댓글 저장 요청 성공")
  void save() throws Exception {
    //given
    CommentSaveRequest request = getCommentSaveRequest();
    CommentSaveResponse response = getCommentSaveResponse();
    String requestJson = objectMapper.registerModule(new JavaTimeModule())
        .writeValueAsString(request);
    String responseJson = objectMapper.writeValueAsString(response);
    doReturn(response).when(commentService).save(any(CommentSaveRequest.class));

    //expected
    mockMvc.perform(post("/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        )
        .andExpect(status().isCreated())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("게시글 별 댓글 조회 요청 성공")
  void find() throws Exception {
    //given
    Page<CommentFindResponse> response = getCommentPage();
    String responseJson = objectMapper.registerModule(new JavaTimeModule())
        .writeValueAsString(response);
    doReturn(response).when(commentService).findByArticleId(eq(1L), any(Pageable.class));

    //expected
    mockMvc.perform(get("/comments/{boardId}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("댓글 수정 요청 성공")
  void update() throws Exception {
    //given
    CommentUpdateRequest request = getCommentUpdateRequest();
    CommentUpdateResponse response = new CommentUpdateResponse(1L);
    String requestJson = objectMapper.registerModule(new JavaTimeModule())
        .writeValueAsString(request);
    String responseJson = objectMapper.writeValueAsString(response);
    doReturn(response).when(commentService).update(any(CommentUpdateRequest.class));

    //expected
    mockMvc.perform(put("/comments/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("댓글 삭제 요청 성공")
  void delete() throws Exception {
    //expected
    mockMvc.perform(MockMvcRequestBuilders.delete("/comments/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  private CommentUpdateRequest getCommentUpdateRequest() {
    return CommentUpdateRequest.builder()
        .id(1L)
        .memberId(1L)
        .content("updatedContent")
        .build();
  }

  private CommentSaveResponse getCommentSaveResponse() {
    return new CommentSaveResponse(1L);
  }

  private CommentSaveRequest getCommentSaveRequest() {
    return CommentSaveRequest.builder()
        .memberId(1L)
        .articleId(1L)
        .parentId(1L)
        .content("content")
        .build();
  }

  private Page<CommentFindResponse> getCommentPage() {
    CommentFindResponse commentA = new CommentFindResponse(getComment(1L));
    CommentFindResponse commentB = new CommentFindResponse(getComment(2L));
    List<CommentFindResponse> list = new ArrayList<>();
    list.add(commentA);
    list.add(commentB);
    return new PageImpl<>(list, PageRequest.of(0, 5), 2);
  }

  private Comment getComment(long commentId) {
    return Comment.builder()
        .id(commentId)
        .memberId(1L)
        .articleId(1L)
        .parentId(1L)
        .content("content")
        .createdAt(LocalDateTime.now())
        .createdBy("member_email")
        .updatedAt(LocalDateTime.now())
        .updatedBy("member_email")
        .build();
  }
}