package enjoytrip.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  LOGIN_FAILED(UNAUTHORIZED),
  INCORRECT_PASSWORD(BAD_REQUEST),
  INVALID_PARAMETER(BAD_REQUEST),
  MEMBER_NOT_FOUND(BAD_REQUEST),
  ARTICLE_NOT_FOUND(BAD_REQUEST),
  ARTICLE_SAVE_FAILED(BAD_REQUEST),
  ARTICLE_UPDATE_FAILED(BAD_REQUEST),
  COMMENT_NOT_FOUND(BAD_REQUEST);

  private final HttpStatus status;

  ErrorCode(HttpStatus status) {
    this.status = status;
  }
}