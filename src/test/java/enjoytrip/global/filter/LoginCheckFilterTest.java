package enjoytrip.global.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import enjoytrip.global.constant.SessionConstant;
import enjoytrip.global.exception.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

@ExtendWith(MockitoExtension.class)
class LoginCheckFilterTest {

  LoginCheckFilter loginCheckFilter = new LoginCheckFilter();
  @Mock
  FilterChain filterChain;

  @Test
  @DisplayName("세션이 존재하지 않으면 메시지가 \"session is null\"인 AuthException 예외가 발생한다.")
  void failAuthByNotExistSession() {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    // expected
    AuthException exception = assertThrows(AuthException.class,
        () -> loginCheckFilter.doFilter(request, response, filterChain));
    assertThat(exception.getMessage()).isEqualTo("session is null");
  }

  @Test
  @DisplayName("세션은 존재하지만 로그인한 사용자가 없다면 메시지가 \"LOGIN_USER is null\"인 AuthException 예외가 발생한다.")
  void failAuthByNotExistLoginUser() {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    request.setSession(new MockHttpSession());

    // expected
    AuthException exception = assertThrows(AuthException.class,
        () -> loginCheckFilter.doFilter(request, response, filterChain));
    assertThat(exception.getMessage()).isEqualTo("LOGIN_USER_ID is null");
  }

  @Test
  @DisplayName("로그인한 사용자가 존재한다면 예외가 발생하지 않는다.")
  void successAuth() throws ServletException, IOException {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockHttpSession session = new MockHttpSession();
    session.setAttribute(SessionConstant.LOGIN_USER_ID.name(), 1L);
    request.setSession(session);

    // expected
    loginCheckFilter.doFilter(request, response, filterChain);

    //then
    verify(filterChain, times(1)).doFilter(any(HttpServletRequest.class),
        any(HttpServletResponse.class));
  }

  @Test
  @DisplayName("home에 접근 시 예외가 발생하지 않는다.")
  void passAuthByHomeURI() throws ServletException, IOException {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("POST");
    request.setRequestURI("/login");
    MockHttpServletResponse response = new MockHttpServletResponse();

    // expected
    loginCheckFilter.doFilter(request, response, filterChain);

    //then
    verify(filterChain, times(1)).doFilter(any(HttpServletRequest.class),
        any(HttpServletResponse.class));
  }

  @Test
  @DisplayName("login에 접근 시 예외가 발생하지 않는다.")
  void passAuthByLoginURI() throws ServletException, IOException {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("GET");
    request.setRequestURI("/");
    MockHttpServletResponse response = new MockHttpServletResponse();

    // expected
    loginCheckFilter.doFilter(request, response, filterChain);

    //then
    verify(filterChain, times(1)).doFilter(any(HttpServletRequest.class),
        any(HttpServletResponse.class));
  }
}