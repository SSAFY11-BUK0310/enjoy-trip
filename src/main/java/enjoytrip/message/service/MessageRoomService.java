package enjoytrip.message.service;

import enjoytrip.article.service.ArticleService;
import enjoytrip.member.service.MemberService;
import enjoytrip.message.domain.MessageRoom;
import enjoytrip.message.dto.request.MessageRoomSaveRequest;
import enjoytrip.message.dto.response.MessageRoomFindResponse;
import enjoytrip.message.dto.response.MessageRoomSaveResponse;
import enjoytrip.message.repository.MessageRoomRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageRoomService {

  private final MessageRoomRepository messageRoomRepository;
  private final MemberService memberService;
  private final MessageService messageService;
  private final ArticleService articleService;

  public MessageRoomSaveResponse save(MessageRoomSaveRequest request) {
    // todo 이미 존재하는 메시지 룸 즉, sender 와 receiver 가 동일한 방이 이미 존재한다면 생성 X
    String writer = memberService.findById(request.getSenderId()).getEmail();
    LocalDateTime currentTime = LocalDateTime.now();
    MessageRoom newMessage = MessageRoom.builder()
        .articleId(request.getArticleId())
        .senderId(request.getSenderId())
        .receiverId(request.getReceiverId())
        .createdAt(currentTime)
        .updatedAt(currentTime)
        .createdBy(writer)
        .updatedBy(writer)
        .build();
    messageRoomRepository.save(newMessage);
    return new MessageRoomSaveResponse(newMessage.getId());
  }

  public Page<MessageRoomFindResponse> findByMemberId(Long memberId, Pageable pageable) {
    List<MessageRoom> messageRoomList = messageRoomRepository.findByMemberId(memberId, pageable);
    List<MessageRoomFindResponse> messageRoomFindResponseList = new ArrayList<>();
    for (MessageRoom messageRoom : messageRoomList) {
      String articleTitle = articleService.findById(messageRoom.getArticleId()).getTitle();
      String receiverEmail = memberService.findById(messageRoom.getReceiverId()).getEmail();
      messageRoomFindResponseList.add(
          new MessageRoomFindResponse(messageRoom, articleTitle, receiverEmail));
    }
    Integer total = messageRoomRepository.countByMemberId(memberId);
    return new PageImpl<>(messageRoomFindResponseList, pageable, total);
  }

  public void delete(Long id) {
    messageService.deleteByMessageRoomId(id);
    messageRoomRepository.delete(id);
  }
}
