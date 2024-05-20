package enjoytrip.message.repository;

import enjoytrip.comment.domain.Comment;
import enjoytrip.message.domain.Message;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

@Mapper
public interface MessageRepository {
  Long save(Message message);

  Optional<Message> findById(Long id);

  List<Message> findByMessageRoomId(@Param("messageRoomId") Long messageRoomId, @Param("pageable") Pageable pageable);

  Integer countByMessageRoomId(@Param("messageRoomId") Long messageRoomId);

  Long update(Message message);

  Long delete(Long id);
}
