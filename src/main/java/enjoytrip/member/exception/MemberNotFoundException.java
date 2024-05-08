package enjoytrip.member.exception;

public class MemberNotFoundException extends RuntimeException {

  private static final String message = "회원을 찾지 못했습니다.";

  public MemberNotFoundException() {
    super(message);
  }

  public MemberNotFoundException(Throwable cause) {
    super(message, cause);
  }
}
