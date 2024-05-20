package enjoytrip.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.Member;
import enjoytrip.member.domain.RoleType;
import enjoytrip.member.dto.request.MemberPasswordUpdateRequest;
import enjoytrip.member.dto.request.MemberSaveRequest;
import enjoytrip.member.dto.request.MemberUpdateRequest;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.dto.response.MemberUpdateResponse;
import enjoytrip.member.exception.IncorrectPasswordException;
import enjoytrip.member.exception.MemberNotFoundException;
import enjoytrip.member.mock.MockMember;
import enjoytrip.member.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

  @InjectMocks
  MemberService memberService;
  @Mock
  MemberRepository memberRepository;

  @Test
  @DisplayName("회원 저장 성공")
  void save() {
    //given
    MemberSaveRequest request = getMemberSaveRequest();
    doReturn(1L).when(memberRepository).save(any(Member.class));

    //when
    memberService.save(request);

    //then
    verify(memberRepository, times(1)).save(any(Member.class));
  }

  @Test
  @DisplayName("회원 id로 조회 성공")
  void findById() {
    //given
    Member member = getMember();
    doReturn(Optional.ofNullable(member)).when(memberRepository).findById(1L);

    //when
    MemberFindResponse response = memberService.findById(1L);

    //then
    assertThat(response).usingRecursiveComparison().isEqualTo(new MemberFindResponse(member));
  }

  @Test
  @DisplayName("회원 email로 조회 성공")
  void findByEmail() {
    //given
    Member member = getMember();
    doReturn(Optional.ofNullable(member)).when(memberRepository).findByEmail(member.getEmail());

    //when
    MemberFindResponse response = memberService.findByEmail(member.getEmail());

    //then
    assertThat(response).usingRecursiveComparison().isEqualTo(new MemberFindResponse(member));
  }

  @Test
  @DisplayName("회원 정보 수정 성공")
  void updateInfo() {
    //given
    MemberUpdateRequest request = getMemberUpdateRequest();
    MockMember mockMember = getMockMember();

    doReturn(Optional.ofNullable(mockMember)).when(memberRepository).findById(1L);
    doReturn(1L).when(memberRepository).update(any(Member.class));

    //when
    MemberUpdateResponse response = memberService.updateInfo(request);

    //then
    assertThat(response.getId()).isEqualTo(1L);
    assertThat(mockMember.isUpdated()).isTrue();
  }

  @Test
  @DisplayName("회원 비밀번호 수정 성공")
  void updatePassword() {
    //given
    MemberPasswordUpdateRequest request = getMemberPasswordUpdateRequest("password", "newPassword",
        "newPassword");
    MockMember mockMember = getMockMember();

    doReturn(Optional.ofNullable(mockMember)).when(memberRepository).findById(1L);
    doReturn(1L).when(memberRepository).update(any(Member.class));

    //when
    MemberUpdateResponse response = memberService.updatePassword(request);

    //then
    assertThat(response.getId()).isEqualTo(1L);
    assertThat(mockMember.isUpdated()).isTrue();
  }

  @Test
  @DisplayName("기존 비밀번호 불일치로 회원 비밀번호 수정 실패")
  void failUpdatePasswordWithMismatchOriginPassword() {
    //given
    MemberPasswordUpdateRequest request = getMemberPasswordUpdateRequest("incorrectPassword",
        "newPassword", "newPassword");
    MockMember mockMember = getMockMember();

    doReturn(Optional.ofNullable(mockMember)).when(memberRepository).findById(1L);

    //expected
    assertThrows(IncorrectPasswordException.class, () -> memberService.updatePassword(request));
  }

  @Test
  @DisplayName("변경하려는 비밀번호들의 불일치로 회원 비밀번호 수정 실패")
  void failUpdatePasswordWithMismatchNewPassword() {
    //given
    MemberPasswordUpdateRequest request = getMemberPasswordUpdateRequest("password",
        "incorrectNewPassword", "incorrectCheckPassword");
    MockMember mockMember = getMockMember();

    doReturn(Optional.ofNullable(mockMember)).when(memberRepository).findById(1L);

    //expected
    assertThrows(IncorrectPasswordException.class, () -> memberService.updatePassword(request));
  }

  @Test
  @DisplayName("존재하지 않는 회원 id로 조회 시 실패")
  void findFailByNoId() {
    //given
    doReturn(Optional.empty()).when(memberRepository).findById(1L);

    //expected
    assertThrows(MemberNotFoundException.class, () -> memberService.findById(1L));
  }

  @Test
  @DisplayName("회원 삭제 성공")
  void delete() {
    //when
    memberService.delete(1L);

    //then
    verify(memberRepository, times(1)).delete(1L);
  }

  private static MemberUpdateRequest getMemberUpdateRequest() {
    return MemberUpdateRequest.builder()
        .id(1L)
        .name("updatedName")
        .age(20)
        .gender(Gender.FEMALE)
        .phoneNumber("010-1234-5678")
        .updatedBy("updatedName")
        .build();
  }

  private static MemberPasswordUpdateRequest getMemberPasswordUpdateRequest(String password,
      String newPassword, String checkPassword) {
    return MemberPasswordUpdateRequest.builder()
        .id(1L)
        .currentPassword(password)
        .newPassword(newPassword)
        .checkPassword(checkPassword)
        .build();
  }

  private static MemberSaveRequest getMemberSaveRequest() {
    return MemberSaveRequest.builder()
        .email("test@email.com")
        .password("password")
        .name("name")
        .age(20)
        .gender(Gender.FEMALE)
        .phoneNumber("010-1234-5678")
        .roleType(RoleType.BASIC)
        .createdBy("name")
        .updatedBy("name")
        .build();
  }

  private static MockMember getMockMember() {
    return new MockMember(1L, "test@email.com", "password", "name", 20, Gender.FEMALE,
        "010-1234-1234",
        RoleType.BASIC, LocalDateTime.now(), LocalDateTime.now(), "name", "name");
  }

  private static Member getMember() {
    return Member.builder()
        .id(1L)
        .email("test@email.com")
        .password("password")
        .name("name")
        .age(20)
        .gender(Gender.FEMALE)
        .phoneNumber("010-1234-5678")
        .createAt(LocalDateTime.now())
        .createdBy("name")
        .updatedAt(LocalDateTime.now())
        .updatedBy("name")
        .build();
  }
}