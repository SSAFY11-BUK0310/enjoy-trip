package enjoytrip.global.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OneWayCipherService {

  private final PasswordEncoder passwordEncoder;

  public String encrypt(String value) {
    return passwordEncoder.encode(value);
  }

  public boolean match(String rawValue, String encryptedValue) {
    return passwordEncoder.matches(rawValue, encryptedValue);
  }
}
