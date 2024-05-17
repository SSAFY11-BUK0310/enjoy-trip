package enjoytrip.global.exception;

import static enjoytrip.global.exception.ErrorCode.INVALID_PARAMETER;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
    ErrorCode errorCode = e.getErrorCode();
    ErrorResponse errorResponse = new ErrorResponse(errorCode, e.getMessage());
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleInvalidParameterException(
      MethodArgumentNotValidException e) {
    ErrorCode errorCode = INVALID_PARAMETER;
    ErrorResponse errorResponse = new ErrorResponse(errorCode, "check for errors in each field");
    errorResponse.addFieldErrors(e.getBindingResult());
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }
}
