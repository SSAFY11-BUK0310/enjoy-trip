package enjoytrip.member.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import enjoytrip.global.constant.SessionConstant;
import enjoytrip.global.exception.AuthException;
import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.Member;
import enjoytrip.member.dto.request.MemberLoginRequest;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.exception.MemberNotFoundException;
import enjoytrip.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

  @MockBean
  MemberService memberService;
  @Autowired
  MockMvc mockMvc;
  ObjectMapper objectMapper;

  @BeforeEach
  void init() {
    objectMapper = new ObjectMapper();
  }

  @Test
  @DisplayName("요청 이메일에 해당하는 회원이 존재하지 않으면 메시지가 \"member not found\"인 AuthException 예외가 발생하고 로그인에 실패한다.")
  void failLoginByNotFoundMember() throws Exception {
    // given
    MemberLoginRequest request = new MemberLoginRequest("test@email.com", "test");
    String requestJson = objectMapper.writeValueAsString(request);
    doThrow(MemberNotFoundException.class).when(memberService).findByEmail(request.getEmail());

    // expected
    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        )
        .andExpect(status().isUnauthorized())
        .andExpect(result -> assertInstanceOf(AuthException.class, result.getResolvedException()))
        .andExpect(
            result -> assertEquals(
                "unable to find member by requested email: " + request.getEmail(),
                result.getResolvedException().getMessage()))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("요청한 비밀번호가 옳지 않다면 메시지가 \"wrong password\"인 AuthException 예외가 발생하고 로그인에 실패한다.")
  void failLoginByWrongPassword() throws Exception {
    // given
    MemberLoginRequest request = new MemberLoginRequest("test@email.com", "wrong password");
    String requestJson = objectMapper.writeValueAsString(request);
    MemberFindResponse memberFindResponse = new MemberFindResponse(getMember());
    doReturn(memberFindResponse).when(memberService).findByEmail(request.getEmail());

    // expected
    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        )
        .andExpect(status().isUnauthorized())
        .andExpect(result -> assertInstanceOf(AuthException.class, result.getResolvedException()))
        .andExpect(
            result -> assertEquals(
                "fail login by wrong password", result.getResolvedException().getMessage()))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("검증을 통과하면 세션을 생성하고 로그인에 성공한다.")
  void successLogin() throws Exception {
    // given
    MemberLoginRequest request = new MemberLoginRequest("test@email.com", "test");
    String requestJson = objectMapper.writeValueAsString(request);
    MemberFindResponse memberFindResponse = new MemberFindResponse(getMember());
    doReturn(memberFindResponse).when(memberService).findByEmail(request.getEmail());

    // expected
    MvcResult mvcResult = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        )
        .andExpect(status().isCreated())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    HttpSession session = mvcResult.getRequest().getSession();
    Assertions.assertThat(session.getAttribute(SessionConstant.LOGIN_USER_ID.name())).isNotNull();
  }

  private static Member getMember() {
    return Member.builder()
        .id(1L)
        .email("test@email.com")
        .password("test")
        .name("Test")
        .age(20)
        .gender(Gender.FEMALE)
        .phoneNumber("010-1234-5678")
        .createAt(LocalDateTime.now())
        .createdBy("Test")
        .updatedAt(LocalDateTime.now())
        .updatedBy("Test")
        .build();
  }
}