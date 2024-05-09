package enjoytrip.article.repository;

import enjoytrip.article.domain.Article;
import enjoytrip.article.dto.request.ArticleFindRequest;
import enjoytrip.article.util.RequestList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ArticleRepository {
    List<Article> findByPage(RequestList requestList);
    Integer count(Article article);
    Optional<Article> findById(Long id);
    Long save(Article article);
    Long update(Article article);
    void delete(Long id);

}

