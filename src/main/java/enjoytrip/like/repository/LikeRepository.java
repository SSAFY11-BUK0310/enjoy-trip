package enjoytrip.like.repository;

import enjoytrip.like.domain.Like;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeRepository {

    Long save(Like like);

    Long deleteByArticleIdAndMemberID(@Param("articleId") Long articleId, @Param("memberId") Long memberId);

    List<Like> findByArticleId(Long articleId);

    List<Like> findByMemberId(Long memberId);

    Long countByArticleId(Long articleId);

    Boolean isExist(@Param("articleId") Long articleId, @Param("memberId") Long memberId);

}
