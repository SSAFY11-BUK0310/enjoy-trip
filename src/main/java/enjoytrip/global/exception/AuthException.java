package enjoytrip.global.exception;

public class AuthException extends BusinessException {

  public AuthException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public AuthException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
