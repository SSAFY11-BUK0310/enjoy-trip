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
import enjoytrip.message.domain.MessageRoom;
import enjoytrip.message.dto.request.MessageRoomSaveRequest;
import enjoytrip.message.dto.response.MessageRoomFindResponse;
import enjoytrip.message.repository.MessageRoomRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
class MessageRoomServiceTest {

  @InjectMocks
  MessageRoomService messageRoomService;

  @Mock
  MessageRoomRepository messageRoomRepository;

  @Mock
  MessageService messageService;

  @Mock
  MemberService memberService;
  LocalDateTime currentTime = LocalDateTime.now();

  @Test
  @DisplayName("메시지 룸 저장 성공")
  void save() {
    //given
    MessageRoomSaveRequest request = new MessageRoomSaveRequest(1L, 1L, 2L);
    MemberFindResponse findMember = getMemberFindResponse();
    doReturn(findMember).when(memberService).findById(request.getSenderId());
    doReturn(1L).when(messageRoomRepository).save(any(MessageRoom.class));

    //when
    messageRoomService.save(request);

    //then
    verify(messageRoomRepository, times(1)).save(any(MessageRoom.class));
  }

  @Test
  @DisplayName("사용자가 참여하고 있는 메시지 룸 조회 성공")
  void findByMemberId() {
    //given
    List<MessageRoom> messageRoomList = getMessageRoomList();
    doReturn(messageRoomList).when(messageRoomRepository)
        .findByMemberId(eq(1L), any(Pageable.class));
    doReturn(10).when(messageRoomRepository).countByMemberId(1L);

    //when
    Page<MessageRoomFindResponse> response = messageRoomService.findByMemberId(1L,
        PageRequest.of(0, 10));

    //then
    assertThat(response.getContent()).usingRecursiveComparison()
        .isEqualTo(messageRoomList.stream().map(MessageRoomFindResponse::new).toList());
  }

  @Test
  @DisplayName("메시지 룸 삭제 성공")
  void delete() {
    //when
    messageRoomService.delete(1L);

    //then
    verify(messageService, times(1)).deleteByMessageRoomId(1L);
    verify(messageRoomRepository, times(1)).delete(1L);
  }

  private List<MessageRoom> getMessageRoomList() {
    List<MessageRoom> list = new ArrayList<>();
    MessageRoom messageRoomA = getMessageRoom();
    MessageRoom messageRoomB = getMessageRoom();
    list.add(messageRoomA);
    list.add(messageRoomB);
    return list;
  }

  private MessageRoom getMessageRoom() {
    return MessageRoom.builder()
        .id(1L)
        .senderId(1L)
        .receiverId(2L)
        .createdAt(currentTime)
        .updatedAt(currentTime)
        .createdBy("memberEmail")
        .updatedBy("memberEmail")
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