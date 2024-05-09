package enjoytrip.article.util;

import enjoytrip.article.domain.Type;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;


@Data
public class RequestList {
    private Type type;
    private String title;
    private Pageable pageable;

    @Builder
    public RequestList(Type type, String title, Pageable pageable) {
        this.type = type;
        this.title = title;
        this.pageable = pageable;
    }
}
