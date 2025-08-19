package KT.PLIP.auth.controller;

import KT.PLIP.auth.dto.LoginRequestDto;
import KT.PLIP.auth.dto.LoginResponseDto;
import KT.PLIP.auth.dto.SignupRequestDto;
import KT.PLIP.auth.dto.SignupResponseDto;
import KT.PLIP.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto requestDto) {
        SignupResponseDto response = authService.signup(requestDto);
        return ResponseEntity.ok(response);
    }
    
    // 로그인 (간단한 평문 비교)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpSession session) {
        // username + password 평문 비교로 로그인
        LoginResponseDto response = authService.login(requestDto);
        
        // 세션에 사용자 정보 저장
        session.setAttribute("userId", response.getId());
        session.setAttribute("username", response.getUsername());
        session.setAttribute("nickname", response.getNickname());
        session.setAttribute("email", response.getEmail());
        
        return ResponseEntity.ok(response);
    }
    
    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃되었습니다.");
    }
    
    // 로그인 상태 확인
    @GetMapping("/check")
    public ResponseEntity<LoginResponseDto> checkLogin(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            LoginResponseDto response = new LoginResponseDto();
            response.setId(userId);
            response.setUsername((String) session.getAttribute("username"));
            response.setEmail((String) session.getAttribute("email"));
            response.setNickname((String) session.getAttribute("nickname"));
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).build();
    }
    
    // 테스트용 엔드포인트
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth 컨트롤러가 정상 작동합니다!");
    }
}
