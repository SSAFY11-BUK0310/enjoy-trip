package enjoytrip.like.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @InjectMocks
    LikeService likeService;

    @Mock
    LikeRepository likeRepository;

    @Test
    @DisplayName("좋아요 저장 요청 성공")
    void save() {
        // given
        LikeSaveRequest request = getLikeSaveRequest();
        doReturn(1L).when(likeRepository).save(any(Like.class));

        // when
        likeService.save(request);

        // then
        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    @DisplayName("좋아요 취소 요청 성공")
    void delete() {
        //given
        LikeDeleteRequest request = LikeDeleteRequest.builder()
            .memberId(1L)
            .articleId(1L)
            .build();
        // when
        likeService.delete(request);
        // then
        verify(likeRepository, times(1)).delete(any(Long.class), any(Long.class));
    }

    @Test
    @DisplayName("게시물 ID로 좋아요 목록 조회 성공")
    void findByArticleId() {
        // given
        List<Like> likeList = getLikeList(5);
        doReturn(likeList).when(likeRepository).findByArticleId(1L);
        // when
        List<LikeFindResponse> response = likeService.findByArticleId(1L);

        // then
        Assertions.assertThat(response).usingRecursiveComparison()
            .isEqualTo(likeList.stream().map(LikeFindResponse::new).toList());
    }

    @Test
    @DisplayName("사용자 ID로 좋아요 목록 조회 성공")
    void findByMemberId() {
        // given
        List<Like> likeList = getLikeList(5);
        doReturn(likeList).when(likeRepository).findByMemberId(1L);
        // when
        List<LikeFindResponse> response = likeService.findByMemberId(1L);

        // then
        Assertions.assertThat(response).usingRecursiveComparison()
            .isEqualTo(likeList.stream().map(LikeFindResponse::new).toList());
    }

    @Test
    @DisplayName("특정 게시물 좋아요 수 요청 성공")
    void countByArticleId() {
        // given
        doReturn(10L).when(likeRepository).countByArticleId(1L);

        // when
        Long response = likeService.countByArticleId(1L);

        // then
        Assertions.assertThat(response).isEqualTo(10L);
    }

    @Test
    void exists() {
        // given
        LikeExistRequest request = LikeExistRequest.builder()
            .articleId(1L)
            .memberId(1L)
            .build();
        doReturn(true).when(likeRepository).isExist(any(Long.class), any(Long.class));

        // when
        Boolean result = likeService.exists(request);
        // then
        Assertions.assertThat(result).isEqualTo(true);
    }

    private static Like getLike(Long id) {
        return Like.builder()
            .id(id)
            .articleId(id)
            .memberId(id)
            .createdAt(LocalDateTime.now())
            .createdBy("dummyEmail")
            .updatedAt(LocalDateTime.now())
            .updatedBy("dummyEmail")
            .build();
    }

    public List<Like> getLikeList(int size) {
        List<Like> response = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            response.add(getLike((long) i));
        }
        return response;
    }

    public static LikeSaveResponse getLiseSaveResponse() {
        LikeSaveResponse response = new LikeSaveResponse();
        response.addId(1L);
        return response;
    }

    private static LikeSaveRequest getLikeSaveRequest() {
        return LikeSaveRequest.builder()
            .memberId(1L)
            .articleId(1L)
            .build();
    }


}