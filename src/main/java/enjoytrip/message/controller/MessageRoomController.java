package enjoytrip.message.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import enjoytrip.message.dto.request.MessageRoomSaveRequest;
import enjoytrip.message.dto.response.MessageRoomFindResponse;
import enjoytrip.message.dto.response.MessageRoomSaveResponse;
import enjoytrip.message.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/message-rooms")
public class MessageRoomController {

  private final MessageRoomService messageRoomService;

  @PostMapping
  public ResponseEntity<MessageRoomSaveResponse> save(
      @RequestBody MessageRoomSaveRequest messageRoomSaveRequest) {
    MessageRoomSaveResponse messageRoomSaveResponse = messageRoomService.save(
        messageRoomSaveRequest);
    return ResponseEntity.status(CREATED).body(messageRoomSaveResponse);
  }

//  @GetMapping("/{id}")
//  public ResponseEntity<MessageRoomFindResponse> findById(@PathVariable("id") Long id) {
//    MessageRoomFindResponse messageRoomFindResponse = messageRoomService.findById(id);
//    return ResponseEntity.status(OK).body(messageRoomFindResponse);
//  }

  @GetMapping("/{memberId}")
  public ResponseEntity<Page<MessageRoomFindResponse>> findById(
      @PathVariable("memberId") Long memberId, @PageableDefault Pageable pageable) {
    Page<MessageRoomFindResponse> messageRoomPage = messageRoomService.findByMemberId(memberId,
        pageable);
    return ResponseEntity.status(OK).body(messageRoomPage);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    messageRoomService.delete(id);
    return ResponseEntity.status(NO_CONTENT).build();
  }
}
