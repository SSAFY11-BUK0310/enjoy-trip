package enjoytrip.article.exception;

import enjoytrip.global.exception.BusinessException;
import enjoytrip.global.exception.ErrorCode;

public class ArticleNotFoundException extends BusinessException {

    public ArticleNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ArticleNotFoundException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
    
}
