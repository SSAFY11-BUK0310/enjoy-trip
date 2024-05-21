package enjoytrip.message.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.Member;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.service.MemberService;
import enjoytrip.message.domain.Message;
import enjoytrip.message.dto.request.MessageSaveRequest;
import enjoytrip.message.dto.request.MessageUpdateRequest;
import enjoytrip.message.dto.response.MessageFindResponse;
import enjoytrip.message.dto.response.MessageUpdateResponse;
import enjoytrip.message.exception.MessageNotFoundException;
import enjoytrip.message.mock.MockMessage;
import enjoytrip.message.repository.MessageRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
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
class MessageServiceTest {

  @InjectMocks
  MessageService messageService;

  @Mock
  MessageRepository messageRepository;

  @Mock
  MemberService memberService;
  LocalDateTime currentTime = LocalDateTime.now();

  @Test
  @DisplayName("메시지 저장 성공")
  void save() {
    //given
    MessageSaveRequest request = getMessageSaveRequest();
    MemberFindResponse findMember = getMemberFindResponse();
    doReturn(findMember).when(memberService).findById(1L);
    doReturn(1L).when(messageRepository).save(any(Message.class));

    //when
    messageService.save(request);

    //then
    verify(messageRepository, times(1)).save(any(Message.class));
  }

  @Test
  @DisplayName("메시지 룸에 존재하는 모든 메시지 조회 성공")
  void findByMessageRoomId() {
    //given
    List<Message> messageList = getMessageList();
    doReturn(messageList).when(messageRepository).findByMessageRoomId(eq(1L), any(Pageable.class));
    doReturn(10).when(messageRepository).countByMessageRoomId(1L);

    //when
    Page<MessageFindResponse> response = messageService.findByMessageRoomId(1L,
        PageRequest.of(0, 10));

    //then
    assertThat(response.getContent()).usingRecursiveComparison()
        .isEqualTo(messageList.stream().map(MessageFindResponse::new).toList());
  }

  @Test
  @DisplayName("메시지 수정 성공")
  void update() {
    //given
    MessageUpdateRequest request = getMessageUpdateRequest();
    MockMessage mockMessage = getMockMessage();
    MemberFindResponse findMember = getMemberFindResponse();
    doReturn(findMember).when(memberService).findById(1L);
    doReturn(Optional.ofNullable(mockMessage)).when(messageRepository).findById(1L);
    doReturn(1L).when(messageRepository).update(any(Message.class));

    //when
    MessageUpdateResponse response = messageService.update(request);

    //then
    assertThat(response.getId()).isEqualTo(1L);
    assertThat(mockMessage.isUpdated()).isTrue();
  }

  @Test
  @DisplayName("존재하지 않는 메시지 수정 실패")
  void findFail() {
    //given
    MessageUpdateRequest request = getMessageUpdateRequest();
    MemberFindResponse findMember = getMemberFindResponse();
    doReturn(findMember).when(memberService).findById(1L);
    doReturn(Optional.empty()).when(messageRepository).findById(1L);

    //expected
    Assertions.assertThrows(MessageNotFoundException.class, () -> messageService.update(request));
  }

  @Test
  @DisplayName("메시지 삭제 성공")
  void delete() {
    //when
    messageService.delete(1L);

    //then
    verify(messageRepository, times(1)).delete(1L);
  }

  @Test
  @DisplayName("메시지 룸에 존재하는 모든 메시지 삭제")
  void deleteByMessageRoomId() {
    //when
    messageService.deleteByMessageRoomId(1L);

    //then
    verify(messageRepository, times(1)).deleteByMessageRoomId(1L);
  }

  private List<Message> getMessageList() {
    List<Message> list = new ArrayList<>();
    Message messageA = getMessage();
    Message messageB = getMessage();
    list.add(messageA);
    list.add(messageB);
    return list;
  }

  private MessageUpdateRequest getMessageUpdateRequest() {
    return MessageUpdateRequest.builder()
        .id(1L)
        .memberId(1L)
        .content("edit content")
        .build();
  }

  private MockMessage getMockMessage() {
    return new MockMessage(1L, 1L, 1L, "content", currentTime, currentTime, "memberEmail",
        "memberEmail");
  }

  private Message getMessage() {
    return Message.builder()
        .id(1L)
        .messageRoomId(1L)
        .memberId(1L)
        .content("content")
        .createdAt(currentTime)
        .updatedAt(currentTime)
        .createdBy("memberEmail")
        .updatedBy("memberEmail")
        .build();
  }

  private MessageSaveRequest getMessageSaveRequest() {
    return MessageSaveRequest.builder()
        .messageRoomId(1L)
        .memberId(1L)
        .content("content")
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