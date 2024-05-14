package enjoytrip.member.service;

import static enjoytrip.global.exception.ErrorCode.MEMBER_NOT_FOUND;

import enjoytrip.member.domain.Member;
import enjoytrip.member.dto.request.MemberSaveRequest;
import enjoytrip.member.dto.request.MemberUpdateRequest;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.dto.response.MemberSaveResponse;
import enjoytrip.member.dto.response.MemberUpdateResponse;
import enjoytrip.member.exception.MemberNotFoundException;
import enjoytrip.member.repository.MemberRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberSaveResponse save(MemberSaveRequest request) {
    Member newMember = Member.builder()
        .email(request.getEmail())
        .password(request.getPassword())
        .name(request.getName())
        .age(request.getAge())
        .gender(request.getGender())
        .phoneNumber(request.getPhoneNumber())
        .roleType(request.getRoleType())
        .createAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .createdBy(request.getCreatedBy())
        .updatedBy(request.getUpdatedBy())
        .build();
    memberRepository.save(newMember);
    return new MemberSaveResponse(newMember.getId());
  }

  public MemberFindResponse findById(Long id) {
    Member findMember = getMemberById(id);
    return new MemberFindResponse(findMember);
  }

  public MemberFindResponse findByEmail(String email) {
    Member findMember = getMemberByEmail(email);
    return new MemberFindResponse(findMember);
  }

  public MemberUpdateResponse update(MemberUpdateRequest request) {
    Member findMember = getMemberById(request.getId());
    findMember.update(
        request.getEmail(),
        request.getPassword(),
        request.getName(),
        request.getAge(),
        request.getGender(),
        request.getPhoneNumber(),
        LocalDateTime.now(),
        request.getUpdatedBy()
    );
    return new MemberUpdateResponse(memberRepository.update(findMember));
  }

  public void delete(Long id) {
    memberRepository.delete(id);
  }

  private Member getMemberById(Long id) {
    return memberRepository.findById(id)
        .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, "does not exist member"));
  }

  private Member getMemberByEmail(String email) {
    return memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, "does not exist member"));
  }
}
