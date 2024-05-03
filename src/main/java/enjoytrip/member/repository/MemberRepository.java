package enjoytrip.member.repository;

import enjoytrip.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberRepository {
    Long save(Member member);
    Optional<Member> findById(Long id);
    Long update(Member member);
    Long delete(Long id);
}
