package enjoytrip.article.exception;

import enjoytrip.global.exception.BusinessException;
import enjoytrip.global.exception.ErrorCode;

public class ArticleSaveFailedException extends BusinessException {

    public ArticleSaveFailedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ArticleSaveFailedException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

}

