package enjoytrip.member.controller;

import static enjoytrip.global.constant.SessionConstant.LOGIN_USER_ID;
import static enjoytrip.global.exception.ErrorCode.LOGIN_FAILED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import enjoytrip.global.exception.AuthException;
import enjoytrip.member.dto.request.MemberLoginRequest;
import enjoytrip.member.dto.response.MemberFindResponse;
import enjoytrip.member.dto.response.MemberLoginResponse;
import enjoytrip.member.exception.MemberNotFoundException;
import enjoytrip.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final MemberService memberService;

  @PostMapping("/login")
  public ResponseEntity<MemberLoginResponse> login(
      @Validated @RequestBody MemberLoginRequest request, HttpServletRequest httpRequest) {
    MemberFindResponse response = getMemberFindResponseWithCheckEmail(request);
    checkPassword(response.getPassword(), request.getPassword());
    HttpSession session = httpRequest.getSession();
    session.setAttribute(LOGIN_USER_ID.name(), response.getId());
    return ResponseEntity.status(CREATED)
        .body(new MemberLoginResponse(response.getId(), response.getName()));
  }


  @GetMapping("/logout")
  public ResponseEntity<Void> logout(HttpSession session) {
    session.invalidate();
    return ResponseEntity.status(NO_CONTENT).build();
  }

  private static void checkPassword(String originalPassword, String requestPassword) {
    if (!originalPassword.equals(requestPassword)) {
      throw new AuthException(LOGIN_FAILED, "fail login by wrong password");
    }
  }

  private MemberFindResponse getMemberFindResponseWithCheckEmail(MemberLoginRequest request) {
    MemberFindResponse response;
    try {
      response = memberService.findByEmail(request.getEmail());
    } catch (MemberNotFoundException e) {
      throw new AuthException(LOGIN_FAILED,
          "unable to find member by requested email: " + request.getEmail(), e);
    }
    return response;
  }
}
