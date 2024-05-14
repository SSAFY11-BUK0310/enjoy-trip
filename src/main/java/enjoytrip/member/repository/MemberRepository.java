package enjoytrip.member.repository;

import enjoytrip.member.domain.Member;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {

  Long save(Member member);

  Optional<Member> findById(Long id);

  Optional<Member> findByEmail(String email);

  Long update(Member member);

  Long delete(Long id);
}
