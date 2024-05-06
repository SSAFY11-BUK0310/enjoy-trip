package enjoytrip.global;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private Integer status;
    private String code;
    private String message;
}
