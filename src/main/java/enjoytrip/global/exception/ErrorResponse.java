package enjoytrip.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private ErrorCode errorCode;

    public ErrorResponse(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
