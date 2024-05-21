package enjoytrip.global.exception;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class ErrorResponse {

  private final ErrorCode errorCode;
  private final String message;
  private List<FieldError> fieldErrors;

  public ErrorResponse(ErrorCode errorCode, String message) {
    this.errorCode = errorCode;
    this.message = message;
  }

  public void addFieldErrors(BindingResult bindingResult) {
    fieldErrors = bindingResult.getFieldErrors()
        .stream()
        .map(error -> new ErrorResponse.FieldError(
            error.getField(),
            error.getDefaultMessage()))
        .collect(Collectors.toList());
  }

  @Getter
  @AllArgsConstructor
  public static class FieldError {

    private final String field;
    private final String message;
  }
}
