package enjoytrip.message.controller;

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
import enjoytrip.message.domain.Message;
import enjoytrip.message.dto.request.MessageSaveRequest;
import enjoytrip.message.dto.request.MessageUpdateRequest;
import enjoytrip.message.dto.response.MessageSaveResponse;
import enjoytrip.message.dto.response.MessageUpdateResponse;
import enjoytrip.message.service.MessageService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

  @InjectMocks
  MessageController messageController;

  @Mock
  MessageService messageService;

  MockMvc mockMvc;

  ObjectMapper objectMapper;

  LocalDateTime currentTime = LocalDateTime.now();

  @BeforeEach
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(messageController)
        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  @DisplayName("메시지 저장 요청 성공")
  void save() throws Exception {
    //given
    MessageSaveRequest request = getMessageSaveRequest();
    MessageSaveResponse response = new MessageSaveResponse(1L);
    String requestJson = objectMapper.registerModule(new JavaTimeModule())
        .writeValueAsString(request);
    String responseJson = objectMapper.writeValueAsString(response);
    doReturn(response).when(messageService).save(any(MessageSaveRequest.class));

    //expected
    mockMvc.perform(post("/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        )
        .andExpect(status().isCreated())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("메시지 룸에 존재하는 메시지 페이지 조회 성공")
  void findByMessageRoomId() throws Exception {
    //given
    Page<Message> response = getMessagePage();
    String responseJson = objectMapper.registerModule(new JavaTimeModule())
        .writeValueAsString(response);
    doReturn(response).when(messageService).findByMessageRoomId(eq(1L), any(Pageable.class));

    //expected
    mockMvc.perform(get("/messages/{messageRoomId}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("메시지 수정 요청 성공")
  void update() throws Exception {
    //given
    MessageUpdateRequest request = getMessageUpdateRequest();
    MessageUpdateResponse response = new MessageUpdateResponse(1L);
    String requestJson = objectMapper.registerModule(new JavaTimeModule())
        .writeValueAsString(request);
    String responseJson = objectMapper.writeValueAsString(response);
    doReturn(response).when(messageService).update(any(MessageUpdateRequest.class));

    //expected
    mockMvc.perform(put("/messages/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("메시지 삭제 요청 성공")
  void delete() throws Exception {
    //expected
    mockMvc.perform(MockMvcRequestBuilders.delete("/messages/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  private MessageUpdateRequest getMessageUpdateRequest() {
    return MessageUpdateRequest.builder()
        .id(1L)
        .memberId(1L)
        .content("edit content")
        .build();
  }

  private Page<Message> getMessagePage() {
    List<Message> list = new ArrayList<>();
    Message messageA = getMessage();
    Message messageB = getMessage();
    list.add(messageA);
    list.add(messageB);
    return new PageImpl<>(list, PageRequest.of(0, 10), 10);
  }

  private Message getMessage() {
    return Message.builder()
        .id(1L)
        .messageRoomId(1L)
        .memberId(1L)
        .content("content")
        .createdAt(currentTime)
        .updatedAt(currentTime)
        .createdBy("memberEmail")
        .updatedBy("memberEmail")
        .build();
  }

  private MessageSaveRequest getMessageSaveRequest() {
    return MessageSaveRequest.builder()
        .messageRoomId(1L)
        .memberId(1L)
        .content("content")
        .build();
  }
}