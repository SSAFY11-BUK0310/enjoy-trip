package enjoytrip.article.exception;

import enjoytrip.global.exception.BusinessException;
import enjoytrip.global.exception.ErrorCode;

public class ArticleUpdateException extends BusinessException {

    public ArticleUpdateException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ArticleUpdateException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

}
