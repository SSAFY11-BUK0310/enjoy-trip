package enjoytrip.comment.exception;

import enjoytrip.global.exception.BusinessException;
import enjoytrip.global.exception.ErrorCode;

public class CommentNotFoundException extends BusinessException {

  public CommentNotFoundException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public CommentNotFoundException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
