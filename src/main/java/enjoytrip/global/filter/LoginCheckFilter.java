package enjoytrip.global.filter;

import static enjoytrip.global.constant.SessionConstant.LOGIN_USER_ID;
import static enjoytrip.global.exception.ErrorCode.LOGIN_FAILED;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
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

  private static final String homePath = "/";
  private static final String loginPath = "/login";
  private static final String joinPath = "/members";
  private static final String docsPath = "/docs/index.html";

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

  private boolean isRequiredAuth(HttpServletRequest request) {
    return !(isHomeURI(request) || isLoginURI(request) || isJoinURI(request) || isPreflight(
        request) || isDocsURI(request));
  }

  private boolean isHomeURI(HttpServletRequest request) {
    boolean isHomeMethod = request.getMethod().equals(GET.name());
    boolean isHomePath = simpleMatch(request.getRequestURI(), homePath);
    return isHomeMethod && isHomePath;
  }

  private boolean isLoginURI(HttpServletRequest request) {
    boolean isLoginMethod = request.getMethod().equals(POST.name());
    boolean isLoginPath = simpleMatch(request.getRequestURI(), loginPath);
    return isLoginMethod && isLoginPath;
  }

  private boolean isJoinURI(HttpServletRequest request) {
    boolean isJoinMethod = request.getMethod().equals(POST.name());
    boolean isJoinPath = simpleMatch(request.getRequestURI(), joinPath);
    return isJoinMethod && isJoinPath;
  }

  private boolean isDocsURI(HttpServletRequest request) {
    boolean isDocsPath = simpleMatch(request.getRequestURI(), docsPath);
    return isDocsPath;
  }

  private boolean isPreflight(HttpServletRequest request) {
    return request.getMethod().equals(OPTIONS.name());
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
