package enjoytrip.message.service;

import enjoytrip.global.exception.ErrorCode;
import enjoytrip.member.service.MemberService;
import enjoytrip.message.domain.Message;
import enjoytrip.message.dto.request.MessageSaveRequest;
import enjoytrip.message.dto.request.MessageUpdateRequest;
import enjoytrip.message.dto.response.MessageFindResponse;
import enjoytrip.message.dto.response.MessageSaveResponse;
import enjoytrip.message.dto.response.MessageUpdateResponse;
import enjoytrip.message.exception.MessageNotFoundException;
import enjoytrip.message.repository.MessageRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final MessageRepository messageRepository;
  private final MemberService memberService;

  public MessageSaveResponse save(MessageSaveRequest request) {
    String writer = memberService.findById(request.getMemberId()).getEmail();
    Message newMessage = Message.builder()
        .memberId(request.getMemberId())
        .messageRoomId(request.getMessageRoomId())
        .content(request.getContent())
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .createdBy(writer)
        .updatedBy(writer)
        .build();
    messageRepository.save(newMessage);
    return new MessageSaveResponse(newMessage.getId());
  }

  public Page<MessageFindResponse> findByMessageRoomId(Long messageRoomId, Pageable pageable) {
    List<MessageFindResponse> messageList = messageRepository.findByMessageRoomId(messageRoomId,
            pageable)
        .stream().map(MessageFindResponse::new).toList();
    Integer total = messageRepository.countByMessageRoomId(messageRoomId);
    return new PageImpl<>(messageList, pageable, total);
  }

  public MessageUpdateResponse update(MessageUpdateRequest request) {
    String writer = memberService.findById(request.getMemberId()).getEmail();
    Message findMessage = getMessage(request.getId());
    findMessage.update(
        request.getContent(),
        writer
    );
    messageRepository.update(findMessage);
    return new MessageUpdateResponse(request.getId());
  }

  public void delete(Long id) {
    messageRepository.delete(id);
  }

  public void deleteByMessageRoomId(Long messageRoomId) {
    messageRepository.deleteByMessageRoomId(messageRoomId);
  }

  private Message getMessage(Long id) {
    return messageRepository.findById(id).orElseThrow(
        () -> new MessageNotFoundException(ErrorCode.MESSAGE_NOT_FOUND, "does not exist Message"));
  }
}
