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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        .parentId(request.getParentId())
        .content(request.getContent())
        .createdAt(LocalDateTime.now())
        .createdBy(writer)
        .updatedAt(LocalDateTime.now())
        .updatedBy(writer)
        .build();
    commentRepository.save(newComment);
    addParentIdToParentComment(newComment);
    return new CommentSaveResponse(newComment.getId());
  }

  public CommentFindResponse findById(Long id) {
    Comment findComment = getCommentById(id);
    return new CommentFindResponse(findComment);
  }

  public Page<CommentFindResponse> findByArticleId(Long articleId, Pageable pageable) {
    Integer total = commentRepository.count(articleId);
    List<CommentFindResponse> list = commentRepository.findByArticleId(articleId, pageable).stream()
        .map(CommentFindResponse::new)
        .toList();
    return new PageImpl<>(list, pageable, total);
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
    Comment findComment = getCommentById(id);
    deleteChildComments(findComment);
    commentRepository.delete(id);
  }

  private void deleteChildComments(Comment findComment) {
    if (findComment.getParentId().equals(findComment.getId())) {
      commentRepository.deleteByParentId(findComment.getId());
    }
  }

  private Comment getCommentById(Long id) {
    return commentRepository.findById(id)
        .orElseThrow(
            () -> new CommentNotFoundException(COMMENT_NOT_FOUND, "does not exist Comment"));
  }

  private void addParentIdToParentComment(Comment newComment) {
    if (newComment.getParentId() == null) {
      newComment.addParentId(newComment.getId());
      commentRepository.update(newComment);
    }
  }
}
