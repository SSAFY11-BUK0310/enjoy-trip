package enjoytrip.comment.repository;

import enjoytrip.comment.domain.Comment;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

@Mapper
public interface CommentRepository {

  Long save(Comment comment);

  Optional<Comment> findById(Long id);

  List<Comment> findByArticleId(@Param("articleId") Long articleId,
      @Param("pageable") Pageable pageable);

  Integer count(@Param("articleId") Long articleId);

  Long update(Comment comment);

  Long delete(Long id);

  void deleteByParentId(Long parentId);
}
