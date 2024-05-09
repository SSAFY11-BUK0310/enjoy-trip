package enjoytrip.article.exception;

public class ArticleNotFoundException extends RuntimeException {

    public static final String MESSAGE = "게시물을 찾지 못했습니다.";

    public ArticleNotFoundException() {
        super(MESSAGE);
    }

}
