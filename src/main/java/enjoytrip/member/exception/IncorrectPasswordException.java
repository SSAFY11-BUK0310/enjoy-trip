package enjoytrip.member.exception;

import enjoytrip.global.exception.BusinessException;
import enjoytrip.global.exception.ErrorCode;

public class IncorrectPasswordException extends BusinessException {

  public IncorrectPasswordException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public IncorrectPasswordException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
