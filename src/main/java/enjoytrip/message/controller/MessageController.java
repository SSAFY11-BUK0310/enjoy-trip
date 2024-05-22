package enjoytrip.message.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import enjoytrip.message.dto.request.MessageSaveRequest;
import enjoytrip.message.dto.request.MessageUpdateRequest;
import enjoytrip.message.dto.response.MessageFindResponse;
import enjoytrip.message.dto.response.MessageSaveResponse;
import enjoytrip.message.dto.response.MessageUpdateResponse;
import enjoytrip.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

  private final MessageService messageService;

  @PostMapping
  public ResponseEntity<MessageSaveResponse> save(
      @RequestBody MessageSaveRequest messageSaveRequest) {
    MessageSaveResponse messageSaveResponse = messageService.save(messageSaveRequest);
    return ResponseEntity.status(CREATED).body(messageSaveResponse);
  }

  @GetMapping("/{messageRoomId}")
  public ResponseEntity<Page<MessageFindResponse>> findByMessageRoomId(
      @PathVariable("messageRoomId") Long messageRoomId, @PageableDefault Pageable pageable) {
    Page<MessageFindResponse> messagePage = messageService.findByMessageRoomId(messageRoomId,
        pageable);
    return ResponseEntity.status(OK).body(messagePage);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MessageUpdateResponse> update(
      @RequestBody MessageUpdateRequest messageUpdateRequest) {
    MessageUpdateResponse messageUpdateResponse = messageService.update(messageUpdateRequest);
    return ResponseEntity.status(OK).body(messageUpdateResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    messageService.delete(id);
    return ResponseEntity.status(NO_CONTENT).build();
  }
}
