package enjoytrip.article.repository;

import enjoytrip.article.domain.Article;
import enjoytrip.article.dto.ArticleFindRequest;
import enjoytrip.article.dto.ArticleFindResponse;
import enjoytrip.article.util.RequestList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface ArticleRepository {
    List<Article> find(RequestList<?> requestList);
    Integer count(ArticleFindRequest articleFindRequest);
    Optional<Article> findById(Long id);
    Long save(Article article);
    void delete(Long id);
    Long update(Article article);


}

