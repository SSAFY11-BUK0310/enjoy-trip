package enjoytrip.like.service;

import enjoytrip.like.domain.Like;
import enjoytrip.like.dto.request.LikeDeleteRequest;
import enjoytrip.like.dto.request.LikeExistRequest;
import enjoytrip.like.dto.request.LikeSaveRequest;
import enjoytrip.like.dto.response.LikeFindResponse;
import enjoytrip.like.dto.response.LikeSaveResponse;
import enjoytrip.like.repository.LikeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeSaveResponse save(LikeSaveRequest request) {
        LocalDateTime now = LocalDateTime.now();
        Like newLike = Like.builder()
            .memberId(request.getMemberId())
            .articleId(request.getArticleId())
            .createdAt(now)
            .updatedAt(now)
            .build();
        Long result = likeRepository.save(newLike);
        if (result == 0) {
            throw new RuntimeException();
        }
        LikeSaveResponse response = new LikeSaveResponse();
        response.addId(newLike.getId());
        return response;
    }

    public void delete(LikeDeleteRequest request) {
        likeRepository.delete(request.getArticleId(), request.getMemberId());
    }

    public List<LikeFindResponse> findByArticleId(Long articleId) {
        List<Like> likes = likeRepository.findByArticleId(articleId);
        return makeLikeFindResponseList(likes);
    }

    public List<LikeFindResponse> findByMemberId(Long memberId) {
        List<Like> likes = likeRepository.findByMemberId(memberId);
        return makeLikeFindResponseList(likes);
    }

    public Long countByArticleId(Long articleId) {
        return likeRepository.countByArticleId(articleId);
    }

    public Boolean exists(LikeExistRequest request) {
        return likeRepository.isExist(request.getArticleId(), request.getMemberId());
    }

    private static List<LikeFindResponse> makeLikeFindResponseList(List<Like> likes) {
        List<LikeFindResponse> response = new ArrayList<>();
        likes.stream().iterator()
            .forEachRemaining(like -> response.add(new LikeFindResponse(like)));
        return response;
    }

}
