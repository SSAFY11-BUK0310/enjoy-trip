package enjoytrip.message.exception;

import enjoytrip.global.exception.BusinessException;
import enjoytrip.global.exception.ErrorCode;

public class MessageRoomNotFoundException extends BusinessException {

  public MessageRoomNotFoundException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public MessageRoomNotFoundException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
