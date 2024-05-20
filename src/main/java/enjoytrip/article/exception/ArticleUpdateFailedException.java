package enjoytrip.article.exception;

import enjoytrip.global.exception.BusinessException;
import enjoytrip.global.exception.ErrorCode;

public class ArticleUpdateFailedException extends BusinessException {

    public ArticleUpdateFailedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ArticleUpdateFailedException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

}

