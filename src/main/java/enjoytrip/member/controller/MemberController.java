package enjoytrip.member.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import enjoytrip.member.dto.request.MemberPasswordUpdateRequest;
import enjoytrip.member.dto.request.MemberSaveRequest;
import enjoytrip.member.dto.request.MemberUpdateRequest;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.dto.response.MemberSaveResponse;
import enjoytrip.member.dto.response.MemberUpdateResponse;
import enjoytrip.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<MemberSaveResponse> save(
      @Validated @RequestBody MemberSaveRequest memberSaveRequest) {
    MemberSaveResponse memberSaveResponse = memberService.save(memberSaveRequest);
    return ResponseEntity.status(CREATED).body(memberSaveResponse);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MemberFindResponse> find(@PathVariable("id") Long id) {
    MemberFindResponse memberFindResponse = memberService.findById(id);
    return ResponseEntity.status(OK).body(memberFindResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MemberUpdateResponse> updateInfo(
      @Validated @RequestBody MemberUpdateRequest memberUpdateRequest) {
    MemberUpdateResponse memberUpdateResponse = memberService.updateInfo(memberUpdateRequest);
    return ResponseEntity.status(OK).body(memberUpdateResponse);
  }

  @PutMapping("/{id}/password")
  public ResponseEntity<MemberUpdateResponse> updatePassword(
      @Validated @RequestBody MemberPasswordUpdateRequest passwordUpdateRequest) {
    MemberUpdateResponse memberUpdateResponse = memberService.updatePassword(passwordUpdateRequest);
    return ResponseEntity.status(OK).body(memberUpdateResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    memberService.delete(id);
    return ResponseEntity.status(NO_CONTENT).build();
  }
}
