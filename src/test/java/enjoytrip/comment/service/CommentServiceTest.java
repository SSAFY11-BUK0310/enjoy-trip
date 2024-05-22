package enjoytrip.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import enjoytrip.comment.domain.Comment;
import enjoytrip.comment.dto.request.CommentSaveRequest;
import enjoytrip.comment.dto.request.CommentUpdateRequest;
import enjoytrip.comment.dto.response.CommentFindResponse;
import enjoytrip.comment.dto.response.CommentUpdateResponse;
import enjoytrip.comment.exception.CommentNotFoundException;
import enjoytrip.comment.mock.MockComment;
import enjoytrip.comment.repository.CommentRepository;
import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.Member;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.service.MemberService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

  @InjectMocks
  CommentService commentService;
  @Mock
  CommentRepository commentRepository;
  @Mock
  MemberService memberService;

  @Test
  @DisplayName("부모 댓글 저장 성공")
  void saveParent() {
    //given
    CommentSaveRequest request = getCommentSaveRequest(null);
    MemberFindResponse findMember = getMemberFindResponse();
    doReturn(findMember).when(memberService).findById(1L);
    doReturn(1L).when(commentRepository).save(any(Comment.class));

    //when
    commentService.save(request);

    //then
    verify(commentRepository, times(1)).save(any(Comment.class));
    verify(commentRepository, times(1)).update(any(Comment.class));
  }

  @Test
  @DisplayName("자식 댓글 저장 성공")
  void saveChild() {
    //given
    CommentSaveRequest request = getCommentSaveRequest(1L);
    MemberFindResponse findMember = getMemberFindResponse();
    doReturn(findMember).when(memberService).findById(1L);
    doReturn(1L).when(commentRepository).save(any(Comment.class));

    //when
    commentService.save(request);

    //then
    verify(commentRepository, times(1)).save(any(Comment.class));
    verify(commentRepository, times(0)).update(any(Comment.class));
  }

  @Test
  @DisplayName("댓글 id로 조회 성공")
  void findById() {
    //given
    Comment comment = getComment(1L);
    doReturn(Optional.ofNullable(comment)).when(commentRepository).findById(1L);

    //when
    CommentFindResponse response = commentService.findById(1L);

    //then
    assertThat(response).usingRecursiveComparison().isEqualTo(new CommentFindResponse(comment));
  }

  @Test
  @DisplayName("게시글 id로 조회 성공")
  void findByBoardId() {
    //given
    List<Comment> commentList = getCommentList();
    doReturn(commentList).when(commentRepository).findByArticleId(eq(1L), any(Pageable.class));
    doReturn(10).when(commentRepository).count(1L);

    //when
    Page<CommentFindResponse> response = commentService.findByArticleId(1L, PageRequest.of(0, 10));

    //then
    assertThat(response.getContent()).usingRecursiveComparison()
        .isEqualTo(commentList.stream().map(CommentFindResponse::new).toList());
  }

  @Test
  @DisplayName("댓글 수정 성공")
  void updateInfo() {
    //given
    CommentUpdateRequest request = getCommentUpdateRequest();
    MockComment mockComment = getMockComment();
    MemberFindResponse findMember = getMemberFindResponse();
    doReturn(findMember).when(memberService).findById(1L);
    doReturn(Optional.ofNullable(mockComment)).when(commentRepository).findById(1L);
    doReturn(1L).when(commentRepository).update(any(Comment.class));

    //when
    CommentUpdateResponse response = commentService.update(request);

    //then
    assertThat(response.getId()).isEqualTo(1L);
    assertThat(mockComment.isUpdated()).isTrue();
  }

  @Test
  @DisplayName("존재하지 않는 댓글 id로 조회 시 실패")
  void findFailByNoId() {
    //given
    doReturn(Optional.empty()).when(commentRepository).findById(1L);

    //expected
    assertThrows(CommentNotFoundException.class, () -> commentService.findById(1L));
  }

  @Test
  @DisplayName("댓글 삭제 성공")
  void delete() {
    //given
    Comment comment = getComment(2L);
    doReturn(Optional.ofNullable(comment)).when(commentRepository).findById(2L);

    //when
    commentService.delete(2L);

    //then
    verify(commentRepository, times(1)).delete(2L);
    verify(commentRepository, times(0)).deleteByParentId(2L);
  }

  @Test
  @DisplayName("부모 댓글 삭제 시, 자식 댓글 모두 삭제 성공")
  void deleteParentComment() {
    //given
    Comment comment = getComment(1L);
    doReturn(Optional.ofNullable(comment)).when(commentRepository).findById(1L);

    //when
    commentService.delete(1L);

    //then
    verify(commentRepository, times(1)).delete(1L);
    verify(commentRepository, times(1)).deleteByParentId(1L);
  }

  private CommentUpdateRequest getCommentUpdateRequest() {
    return CommentUpdateRequest.builder()
        .id(1L)
        .memberId(1L)
        .content("updatedContent")
        .build();
  }

  private CommentSaveRequest getCommentSaveRequest(Long parentId) {
    return CommentSaveRequest.builder()
        .memberId(1L)
        .articleId(1L)
        .parentId(parentId)
        .content("content")
        .build();
  }

  private MockComment getMockComment() {
    return new MockComment(1L, 1L, 1L, 1L, "content", LocalDateTime.now(), LocalDateTime.now(),
        "memberEmail", "memberEmail");
  }

  private List<Comment> getCommentList() {
    Comment commentA = getComment(2L);
    Comment commentB = getComment(3L);
    List<Comment> list = new ArrayList<>();
    list.add(commentA);
    list.add(commentB);
    return list;
  }

  private Comment getComment(long commentId) {
    return Comment.builder()
        .id(commentId)
        .memberId(1L)
        .articleId(1L)
        .parentId(1L)
        .content("content")
        .createdAt(LocalDateTime.now())
        .createdBy("member_email")
        .updatedAt(LocalDateTime.now())
        .updatedBy("member_email")
        .build();
  }

  private MemberFindResponse getMemberFindResponse() {
    Member member = Member.builder()
        .id(1L)
        .email("test@email.com")
        .password("password")
        .name("name")
        .age(20)
        .gender(Gender.FEMALE)
        .phoneNumber("010-1234-5678")
        .createAt(LocalDateTime.now())
        .createdBy("name")
        .updatedAt(LocalDateTime.now())
        .updatedBy("name")
        .build();
    return new MemberFindResponse(member);
  }
}