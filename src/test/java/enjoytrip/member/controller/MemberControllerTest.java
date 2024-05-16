package enjoytrip.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.Member;
import enjoytrip.member.dto.request.MemberPasswordUpdateRequest;
import enjoytrip.member.dto.request.MemberSaveRequest;
import enjoytrip.member.dto.request.MemberUpdateRequest;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.dto.response.MemberSaveResponse;
import enjoytrip.member.dto.response.MemberUpdateResponse;
import enjoytrip.member.service.MemberService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

  @InjectMocks
  MemberController memberController;
  @Mock
  MemberService memberService;
  MockMvc mockMvc;
  ObjectMapper objectMapper;

  @BeforeEach
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  @DisplayName("회원 저장 요청 성공")
  void save() throws Exception {
    //given
    MemberSaveRequest request = getMemberSaveRequest();
    MemberSaveResponse response = getMemberSaveResponse();
    String requestJson = objectMapper.registerModule(new JavaTimeModule())
        .writeValueAsString(request);
    String responseJson = objectMapper.writeValueAsString(response);
    doReturn(response).when(memberService).save(any(MemberSaveRequest.class));

    //expected
    mockMvc.perform(MockMvcRequestBuilders.post("/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        )
        .andExpect(status().isCreated())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("회원 조회 요청 성공")
  void find() throws Exception {
    //given
    MemberFindResponse response = new MemberFindResponse(getMember());
    ;
    String responseJson = objectMapper.registerModule(new JavaTimeModule())
        .writeValueAsString(response);
    doReturn(response).when(memberService).findById(1L);

    //expected
    mockMvc.perform(MockMvcRequestBuilders.get("/members/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("회원 정보 수정 요청 성공")
  void updateInfo() throws Exception {
    //given
    MemberUpdateRequest request = getMemberUpdateRequest();
    MemberUpdateResponse response = new MemberUpdateResponse(1L);
    String requestJson = objectMapper.registerModule(new JavaTimeModule())
        .writeValueAsString(request);
    String responseJson = objectMapper.writeValueAsString(response);
    doReturn(response).when(memberService).updateInfo(any(MemberUpdateRequest.class));

    //expected
    mockMvc.perform(MockMvcRequestBuilders.put("/members/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("회원 비밀번호 수정 요청 성공")
  void updatePassword() throws Exception {
    //given
    MemberPasswordUpdateRequest request = getMemberPasswordUpdateRequest();
    MemberUpdateResponse response = new MemberUpdateResponse(1L);
    String requestJson = objectMapper.writeValueAsString(request);
    String responseJson = objectMapper.writeValueAsString(response);
    doReturn(response).when(memberService).updatePassword(any(MemberPasswordUpdateRequest.class));

    //expected
    mockMvc.perform(MockMvcRequestBuilders.put("/members/{id}/password", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(responseJson))
        .andDo(print());
  }

  @Test
  @DisplayName("회원 삭제 요청 성공")
  void delete() throws Exception {
    //expected
    mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  private static MemberUpdateRequest getMemberUpdateRequest() {
    return MemberUpdateRequest.builder()
        .id(1L)
        .name("name")
        .age(20)
        .gender(Gender.FEMALE)
        .phoneNumber("010-1234-5678")
        .updatedBy("name")
        .build();
  }

  private static MemberPasswordUpdateRequest getMemberPasswordUpdateRequest() {
    return MemberPasswordUpdateRequest.builder()
        .id(1L)
        .currentPassword("password")
        .newPassword("newPassword")
        .checkPassword("newPassword")
        .build();
  }

  private static MemberSaveResponse getMemberSaveResponse() {
    return new MemberSaveResponse(1L);
  }

  private static MemberSaveRequest getMemberSaveRequest() {
    return MemberSaveRequest.builder()
        .email("test@email.com")
        .password("password")
        .name("name")
        .age(20)
        .gender(Gender.FEMALE)
        .phoneNumber("010-1234-5678")
        .createdBy("name")
        .updatedBy("name")
        .build();
  }

  private static Member getMember() {
    return Member.builder()
        .id(1L)
        .email("test@email.com")
        .password("password")
        .name("name")
        .age(20)
        .gender(Gender.FEMALE)
        .phoneNumber("010-1234-5678")
        .createAt(LocalDateTime.now())
        .createdBy("name")
        .updatedAt(LocalDateTime.now())
        .updatedBy("name")
        .build();
  }
}