package enjoytrip.member.service;

import enjoytrip.member.domain.Member;
import enjoytrip.member.dto.request.MemberSaveRequest;
import enjoytrip.member.dto.request.MemberUpdateRequest;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.dto.response.MemberSaveResponse;
import enjoytrip.member.dto.response.MemberUpdateResponse;
import enjoytrip.member.exception.MemberNotFoundException;
import enjoytrip.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
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
                .createAt(request.getCreateAt())
                .updatedAt(request.getUpdatedAt())
                .createdBy(request.getCreatedBy())
                .createdBy(request.getUpdatedBy())
                .build();

        return new MemberSaveResponse(memberRepository.save(newMember));
    }

    public MemberFindResponse find(Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return new MemberFindResponse(findMember);
    }

    public MemberUpdateResponse update(MemberUpdateRequest request) {
        Member findMember = memberRepository.findById(request.getId()).orElseThrow(MemberNotFoundException::new);
        findMember.update(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getAge(),
                request.getGender(),
                request.getPhoneNumber(),
                request.getUpdatedAt(),
                request.getUpdatedBy()
        );
        return new MemberUpdateResponse(memberRepository.update(findMember));
    }

    public void delete(Long id) {
        memberRepository.delete(id);
    }
}
