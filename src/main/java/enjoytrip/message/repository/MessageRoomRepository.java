package enjoytrip.message.repository;

import enjoytrip.message.domain.Message;
import enjoytrip.message.domain.MessageRoom;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

@Mapper
public interface MessageRoomRepository {
  Long save(MessageRoom messageRoom);

  Optional<MessageRoom> findById(Long id);

  List<MessageRoom> findByMemberId(@Param("memberId") Long memberId, @Param("pageable") Pageable pageable);

  Integer countByMemberId(@Param("memberId") Long memberId);

  Long delete(Long id);
}
