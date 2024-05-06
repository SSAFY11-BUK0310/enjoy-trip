package enjoytrip.article.service;

import enjoytrip.article.domain.Article;
import enjoytrip.article.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ArticleService {
    ArticleFindResponse findById(Long id);
    ArticleSaveResponse save(ArticleSaveRequest articleSaveRequest) throws IOException;

    ArticleUpdateResponse update(ArticleUpdateRequest articleUpdateRequest, MultipartFile updateImage) throws IOException;
    void delete(Long id);

    Page<Map<String, Object>> find(ArticleFindRequest articleFindRequest, Pageable pageable);


}
