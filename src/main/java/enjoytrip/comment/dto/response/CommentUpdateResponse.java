package enjoytrip.comment.dto.response;

import lombok.Getter;

@Getter
public class CommentUpdateResponse {

  private Long id;

  public CommentUpdateResponse(Long id) {
    this.id = id;
  }
}
