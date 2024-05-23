package enjoytrip.member.service;

import static enjoytrip.global.exception.ErrorCode.MEMBER_NOT_FOUND;

import enjoytrip.global.exception.ErrorCode;
import enjoytrip.global.service.OneWayCipherService;
import enjoytrip.member.domain.Member;
import enjoytrip.member.dto.request.MemberPasswordUpdateRequest;
import enjoytrip.member.dto.request.MemberSaveRequest;
import enjoytrip.member.dto.request.MemberUpdateRequest;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.dto.response.MemberSaveResponse;
import enjoytrip.member.dto.response.MemberUpdateResponse;
import enjoytrip.member.exception.IncorrectPasswordException;
import enjoytrip.member.exception.MemberNotFoundException;
import enjoytrip.member.repository.MemberRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final OneWayCipherService oneWayCipherService;

  public MemberSaveResponse save(MemberSaveRequest request) {
    String encryptedPassword = oneWayCipherService.encrypt(request.getPassword());
    LocalDateTime currentTime = LocalDateTime.now();
    String email = request.getEmail();
    Member newMember = Member.builder()
        .email(email)
        .password(encryptedPassword)
        .name(request.getName())
        .age(request.getAge())
        .gender(request.getGender())
        .phoneNumber(request.getPhoneNumber())
        .roleType(request.getRoleType())
        .createAt(currentTime)
        .updatedAt(currentTime)
        .createdBy(email)
        .updatedBy(email)
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

  public MemberUpdateResponse updateInfo(MemberUpdateRequest request) {
    Member findMember = getMemberById(request.getId());
    findMember.updateInfo(
        request.getName(),
        request.getAge(),
        request.getGender(),
        request.getPhoneNumber(),
        LocalDateTime.now(),
        request.getUpdatedBy()
    );
    return new MemberUpdateResponse(memberRepository.update(findMember));
  }

  public MemberUpdateResponse updatePassword(MemberPasswordUpdateRequest request) {
    Member findMember = getMemberById(request.getId());
    checkOriginPassword(findMember.getPassword(), request.getCurrentPassword());
    checkNewPassword(request.getNewPassword(), request.getCheckPassword());
    findMember.updatePassword(request.getNewPassword());
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

  private void checkOriginPassword(String originPassword, String requestedPassword) {
    if (!originPassword.equals(requestedPassword)) {
      throw new IncorrectPasswordException(ErrorCode.INCORRECT_PASSWORD,
          "mismatch with existing password");
    }
  }

  private void checkNewPassword(String newPassword, String checkPassword) {
    if (!newPassword.equals(checkPassword)) {
      throw new IncorrectPasswordException(ErrorCode.INCORRECT_PASSWORD,
          "new passwords do not match");
    }
  }
}
