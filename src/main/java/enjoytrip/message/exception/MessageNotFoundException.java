package enjoytrip.message.exception;

import enjoytrip.global.exception.BusinessException;
import enjoytrip.global.exception.ErrorCode;

public class MessageNotFoundException extends BusinessException {

  public MessageNotFoundException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public MessageNotFoundException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
