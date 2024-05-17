package enjoytrip.comment.dto.response;

import lombok.Getter;

@Getter
public class CommentSaveResponse {

  private Long id;

  public CommentSaveResponse(Long id) {
    this.id = id;
  }
}
