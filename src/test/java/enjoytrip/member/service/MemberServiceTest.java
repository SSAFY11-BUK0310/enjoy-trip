package enjoytrip.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import enjoytrip.member.domain.Gender;
import enjoytrip.member.domain.Member;
import enjoytrip.member.dto.request.MemberSaveRequest;
import enjoytrip.member.dto.request.MemberUpdateRequest;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.dto.response.MemberSaveResponse;
import enjoytrip.member.dto.response.MemberUpdateResponse;
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
    MemberSaveResponse response = memberService.save(request);

    //then
    assertThat(response.getId()).isEqualTo(1L);
  }

  @Test
  @DisplayName("회원 id로 조회 성공")
  void find() {
    //given
    Member member = getMember();
    doReturn(Optional.ofNullable(member)).when(memberRepository).findById(1L);

    //when
    MemberFindResponse response = memberService.find(1L);

    //then
    assertThat(response).usingRecursiveComparison().isEqualTo(new MemberFindResponse(member));
  }

  @Test
  @DisplayName("회원 수정 성공")
  void update() {
    //given
    MemberUpdateRequest request = getMemberUpdateRequest();
    MockMember mockMember = getMockMember();

    doReturn(Optional.ofNullable(mockMember)).when(memberRepository).findById(1L);
    doReturn(1L).when(memberRepository).update(any(Member.class));

    //when
    MemberUpdateResponse response = memberService.update(request);

    //then
    assertThat(response.getId()).isEqualTo(1L);
    assertThat(mockMember.isUpdated()).isTrue();
  }

  @Test
  @DisplayName("존재하지 않는 회원 id로 조회 시 실패")
  void findFailByNoId() {
    //given
    doReturn(Optional.empty()).when(memberRepository).findById(1L);

    //expected
    assertThrows(MemberNotFoundException.class, () -> memberService.find(1L));
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
        .email("re_test@email.com")
        .password("re_test")
        .name("re_Test")
        .age(20)
        .gender(Gender.FEMALE)
        .phoneNumber("010-1234-5678")
        .updatedAt(LocalDateTime.now())
        .updatedBy("re_Test")
        .build();
  }

  private static MemberSaveRequest getMemberSaveRequest() {
    return MemberSaveRequest.builder()
        .email("test@email.com")
        .password("test")
        .name("Test")
        .age(20)
        .gender(Gender.FEMALE)
        .phoneNumber("010-1234-5678")
        .createAt(LocalDateTime.now())
        .createdBy("Test")
        .updatedAt(LocalDateTime.now())
        .updatedBy("Test")
        .build();
  }

  private static MockMember getMockMember() {
    return new MockMember(1L, "test@email.com", "test1234", "username", 20, Gender.FEMALE,
        "010-1234-1234", LocalDateTime.now(), LocalDateTime.now(), "username", "username");
  }

  private static Member getMember() {
    return Member.builder()
        .id(1L)
        .email("test@email.com")
        .password("test")
        .name("Test")
        .age(20)
        .gender(Gender.FEMALE)
        .phoneNumber("010-1234-5678")
        .createAt(LocalDateTime.now())
        .createdBy("Test")
        .updatedAt(LocalDateTime.now())
        .updatedBy("Test")
        .build();
  }
}