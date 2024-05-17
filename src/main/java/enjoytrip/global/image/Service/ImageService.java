package enjoytrip.global.image.Service;

import enjoytrip.global.image.domain.Image;
import enjoytrip.global.image.repository.ImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Long save(Image articleImage) {
        return imageRepository.save(articleImage);
    }

    public  List<Image> findByArticleId(Long articleId) {
        return imageRepository.findByArticleId(articleId);
    }

    public Long deleteByArticleId(Long articleId) {
        return imageRepository.deleteByArticleId(articleId);
    }
}
