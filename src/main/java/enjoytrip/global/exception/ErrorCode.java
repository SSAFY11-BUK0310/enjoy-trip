package enjoytrip.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    LOGIN_FAILED(UNAUTHORIZED),
    MEMBER_NOT_FOUND(BAD_REQUEST),
    ARTICLE_NOT_FOUND(BAD_REQUEST);

    private final HttpStatus status;

    ErrorCode(HttpStatus status) {
        this.status = status;
    }
}