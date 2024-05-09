package enjoytrip.global.filter;

import static enjoytrip.global.constant.SessionConstant.LOGIN_USER_ID;
import static enjoytrip.global.exception.ErrorCode.LOGIN_FAILED;
import static org.springframework.util.PatternMatchUtils.simpleMatch;

import enjoytrip.global.exception.AuthException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckFilter implements Filter {

  private static final String homeMethod = "GET";
  private static final String homePath = "/";
  private static final String loginMethod = "POST";
  private static final String loginPath = "/login";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    if (isRequiredAuth(httpRequest)) {
      HttpSession session = httpRequest.getSession(false);
      validate(session);
    }
    chain.doFilter(request, response);
  }

  private static boolean isRequiredAuth(HttpServletRequest request) {
    return !(isHomeURI(request) || isLoginURI(request));
  }

  private static boolean isHomeURI(HttpServletRequest request) {
    boolean isHomeMethod = request.getMethod().equals(homeMethod);
    boolean isHomePath = simpleMatch(request.getRequestURI(), homePath);
    return isHomeMethod && isHomePath;
  }

  private static boolean isLoginURI(HttpServletRequest request) {
    boolean isLoginMethod = request.getMethod().equals(loginMethod);
    boolean isLoginPath = simpleMatch(request.getRequestURI(), loginPath);
    return isLoginMethod && isLoginPath;
  }

  private void validate(HttpSession session) {
    if (session == null) {
      throw new AuthException(LOGIN_FAILED, "session is null");
    }
    if (session.getAttribute(LOGIN_USER_ID.name()) == null) {
      throw new AuthException(LOGIN_FAILED, "LOGIN_USER_ID is null");
    }
  }
}
