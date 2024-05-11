package enjoytrip.article.exception;

public class ArticleUpdateException extends RuntimeException {

    public static final String MESSAGE = "갱신에 실패했습니다.";

    public ArticleUpdateException() {
        super(MESSAGE);
    }

}
