package enjoytrip.like.repository;

import enjoytrip.like.domain.Like;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeRepository {

    Long save(Like like);

    Long delete(Long id);

    List<Like> findByArticleId(Long articleId);

    List<Like> findByMemberId(Long memberId);

}
