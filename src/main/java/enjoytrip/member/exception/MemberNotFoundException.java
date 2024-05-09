package enjoytrip.member.exception;

import enjoytrip.global.exception.BusinessException;
import enjoytrip.global.exception.ErrorCode;

public class MemberNotFoundException extends BusinessException {

  public MemberNotFoundException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public MemberNotFoundException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
