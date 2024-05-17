package enjoytrip.comment.repository;

import enjoytrip.comment.domain.Comment;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

@Mapper
public interface CommentRepository {

  Long save(Comment comment);

  Optional<Comment> findById(Long id);

  Page<Comment> findByBoardId(Long boardId);

  Long update(Comment comment);

  Long delete(Long id);
}
