package enjoytrip.member.controller;

import enjoytrip.member.dto.request.MemberSaveRequest;
import enjoytrip.member.dto.request.MemberUpdateRequest;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.dto.response.MemberSaveResponse;
import enjoytrip.member.dto.response.MemberUpdateResponse;
import enjoytrip.member.service.MemberService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberSaveResponse> save(@RequestBody MemberSaveRequest memberSaveRequest) {
        MemberSaveResponse memberSaveResponse = memberService.save(memberSaveRequest);
        return ResponseEntity.status(CREATED).body(memberSaveResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberFindResponse> find(@PathVariable Long id) {
        MemberFindResponse memberFindResponse = memberService.find(id);
        return ResponseEntity.status(OK).body(memberFindResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberUpdateResponse> update(@RequestBody MemberUpdateRequest memberUpdateRequest) {
        MemberUpdateResponse memberUpdateResponse = memberService.update(memberUpdateRequest);
        return ResponseEntity.status(OK).body(memberUpdateResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
