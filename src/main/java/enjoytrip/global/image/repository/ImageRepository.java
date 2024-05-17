package enjoytrip.global.image.repository;

import enjoytrip.global.image.domain.Image;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageRepository {

    Long save(Image articleImage);

    List<Image> findByArticleId(Long articleId);

    Long deleteByArticleId(Long articleId);

}
