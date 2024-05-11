package enjoytrip.article.exception;

public class ArticleSaveException extends RuntimeException {

    public static final String MESSAGE = "등록을 실패했습니다.";

    public ArticleSaveException() {
        super(MESSAGE);
    }

}
