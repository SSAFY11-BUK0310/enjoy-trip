package enjoytrip.comment.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import enjoytrip.comment.dto.request.CommentSaveRequest;
import enjoytrip.comment.dto.request.CommentUpdateRequest;
import enjoytrip.comment.dto.response.CommentFindResponse;
import enjoytrip.comment.dto.response.CommentSaveResponse;
import enjoytrip.comment.dto.response.CommentUpdateResponse;
import enjoytrip.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<CommentSaveResponse> save(
      @RequestBody CommentSaveRequest commentSaveRequest) {
    CommentSaveResponse commentSaveResponse = commentService.save(commentSaveRequest);
    return ResponseEntity.status(CREATED).body(commentSaveResponse);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CommentFindResponse> find(@PathVariable("id") Long id) {
    CommentFindResponse commentFindResponse = commentService.findById(id);
    return ResponseEntity.status(OK).body(commentFindResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CommentUpdateResponse> update(
      @RequestBody CommentUpdateRequest commentUpdateRequest) {
    CommentUpdateResponse commentUpdateResponse = commentService.update(commentUpdateRequest);
    return ResponseEntity.status(OK).body(commentUpdateResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    commentService.delete(id);
    return ResponseEntity.status(NO_CONTENT).build();
  }
}
