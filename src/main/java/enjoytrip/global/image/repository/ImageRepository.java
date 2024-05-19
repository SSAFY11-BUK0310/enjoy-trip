package enjoytrip.global.image.repository;

import enjoytrip.global.image.domain.Image;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageRepository {

    Long save(Image image);

    List<Image> findByArticleId(Long articleId);

    Image findByImageUUID(String imageUUID);

    Long deleteByImageUUID(String imageUUID);

    Long deleteByDirectoryUUID(String directoryUUID);

    Long deleteByArticleId(Long articleId);

}
