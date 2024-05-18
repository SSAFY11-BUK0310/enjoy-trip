package enjoytrip.comment.service;

import static enjoytrip.global.exception.ErrorCode.COMMENT_NOT_FOUND;

import enjoytrip.comment.domain.Comment;
import enjoytrip.comment.dto.request.CommentSaveRequest;
import enjoytrip.comment.dto.request.CommentUpdateRequest;
import enjoytrip.comment.dto.response.CommentFindResponse;
import enjoytrip.comment.dto.response.CommentSaveResponse;
import enjoytrip.comment.dto.response.CommentUpdateResponse;
import enjoytrip.comment.exception.CommentNotFoundException;
import enjoytrip.comment.repository.CommentRepository;
import enjoytrip.member.service.MemberService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final MemberService memberService;

  public CommentSaveResponse save(CommentSaveRequest request) {
    String writer = memberService.findById(request.getMemberId()).getEmail();
    Comment newComment = Comment.builder()
        .memberId(request.getMemberId())
        .articleId(request.getArticleId())
        .childId(request.getChildId())
        .content(request.getContent())
        .createdAt(LocalDateTime.now())
        .createdBy(writer)
        .updatedAt(LocalDateTime.now())
        .updatedBy(writer)
        .build();
    commentRepository.save(newComment);
    return new CommentSaveResponse(newComment.getId());
  }

  public CommentFindResponse findById(Long id) {
    Comment findComment = getCommentById(id);
    return new CommentFindResponse(findComment);
  }

  public Page<CommentFindResponse> findByBoardId(Long boardId) {
    return commentRepository.findByBoardId(boardId).map(CommentFindResponse::new);
  }

  public CommentUpdateResponse update(CommentUpdateRequest request) {
    String writer = memberService.findById(request.getMemberId()).getEmail();
    Comment findComment = getCommentById(request.getId());
    findComment.update(
        request.getContent(),
        LocalDateTime.now(),
        writer
    );
    return new CommentUpdateResponse(commentRepository.update(findComment));
  }

  public void delete(Long id) {
    commentRepository.delete(id);
  }

  private Comment getCommentById(Long id) {
    return commentRepository.findById(id)
        .orElseThrow(
            () -> new CommentNotFoundException(COMMENT_NOT_FOUND, "does not exist Comment"));
  }
}
