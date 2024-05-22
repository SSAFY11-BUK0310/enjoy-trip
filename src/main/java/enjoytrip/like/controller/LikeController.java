package enjoytrip.like.controller;

import enjoytrip.like.dto.request.LikeDeleteRequest;
import enjoytrip.like.dto.request.LikeExistRequest;
import enjoytrip.like.dto.request.LikeSaveRequest;
import enjoytrip.like.dto.response.LikeFindResponse;
import enjoytrip.like.dto.response.LikeSaveResponse;
import enjoytrip.like.service.LikeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeSaveResponse> save(@RequestBody LikeSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.save(request));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody LikeDeleteRequest request) {
        likeService.delete(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/{memberId}/memberId")
    public ResponseEntity<List<LikeFindResponse>> findByMemberId(
        @PathVariable("memberId") Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.findByMemberId(memberId));
    }

    @GetMapping("/{articleId}/articleId")
    public ResponseEntity<List<LikeFindResponse>> findByArticleId(
        @PathVariable("articleId") Long articleId) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.findByArticleId(articleId));
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> exists(LikeExistRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.exists(request));
    }
}
