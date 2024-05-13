package enjoytrip.article.exception;

import enjoytrip.global.exception.BusinessException;
import enjoytrip.global.exception.ErrorCode;

public class ArticleSaveException extends BusinessException {

    public ArticleSaveException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ArticleSaveException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

}
