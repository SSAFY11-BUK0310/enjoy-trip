package enjoytrip.article.exception;

public class ArticleTypeMismatchException extends RuntimeException {

    public static final String MESSAGE = "타입이 일치하지 않습니다.";

    public ArticleTypeMismatchException(){ super(MESSAGE); }
}
