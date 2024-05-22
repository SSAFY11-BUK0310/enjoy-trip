package enjoytrip.article.repository;

import enjoytrip.article.domain.Article;
import enjoytrip.article.domain.ArticleType;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

@Mapper
public interface ArticleRepository {

    Long save(Article article);

    List<Article> findByPage(@Param("articleType") ArticleType articleType,
        @Param("title") String title, @Param("pageable") Pageable pageable);

    Integer count(@Param("articleType") ArticleType articleType, @Param("title") String title);

    Optional<Article> findById(Long id);

    Long update(Article article);

    void delete(Long id);

    Long getViews(Long id);

    Long updateViews(Long id);
}

