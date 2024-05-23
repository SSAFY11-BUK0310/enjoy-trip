package enjoytrip.message.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import enjoytrip.config.AbstractRestDocsTest;
import enjoytrip.message.domain.MessageRoom;
import enjoytrip.message.dto.request.MessageRoomSaveRequest;
import enjoytrip.message.dto.response.MessageRoomSaveResponse;
import enjoytrip.message.service.MessageRoomService;
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

@WebMvcTest(MessageRoomController.class)
class MessageRoomControllerRestDocsTest extends AbstractRestDocsTest {

  @MockBean
  MessageRoomService messageRoomService;

  LocalDateTime currentTime = LocalDateTime.now();

  @Test
  @DisplayName("메시지 룸 저장 요청 성공")
  void save() throws Exception {
    //given
    MessageRoomSaveRequest request = new MessageRoomSaveRequest(1L, 1L, 2L);
    MessageRoomSaveResponse response = new MessageRoomSaveResponse(1L);
    String requestJson = objectMapper.registerModule(new JavaTimeModule())
        .writeValueAsString(request);
    String responseJson = objectMapper.writeValueAsString(response);
    doReturn(response).when(messageRoomService).save(any(MessageRoomSaveRequest.class));

    //expected
    mockMvc.perform(post("/message-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        )
        .andExpect(status().isCreated())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("사용자가 참여한 메시지 룸 페이지 조회 성공")
  void findByMemberId() throws Exception {
    //given
    Page<MessageRoom> response = getMessageRoomPage();
    String responseJson = objectMapper.registerModule(new JavaTimeModule())
        .writeValueAsString(response);
    doReturn(response).when(messageRoomService).findByMemberId(eq(1L), any(Pageable.class));

    //expected
    mockMvc.perform(get("/message-rooms/{memberId}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("메시지 룸 삭제 요청 성공")
  void delete() throws Exception {
    //expected
    mockMvc.perform(MockMvcRequestBuilders.delete("/message-rooms/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  private Page<MessageRoom> getMessageRoomPage() {
    List<MessageRoom> list = new ArrayList<>();
    MessageRoom messageRoomA = getMessageRoom();
    MessageRoom messageRoomB = getMessageRoom();
    list.add(messageRoomA);
    list.add(messageRoomB);
    return new PageImpl<>(list, PageRequest.of(0, 10), 10);
  }

  private MessageRoom getMessageRoom() {
    return MessageRoom.builder()
        .id(1L)
        .senderId(1L)
        .receiverId(2L)
        .createdAt(currentTime)
        .updatedAt(currentTime)
        .createdBy("memberEmail")
        .updatedBy("memberEmail")
        .build();
  }
}