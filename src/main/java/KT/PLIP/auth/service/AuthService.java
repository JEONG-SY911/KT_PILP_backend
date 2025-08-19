package KT.PLIP.auth.service;

import KT.PLIP.auth.dto.LoginRequestDto;
import KT.PLIP.auth.dto.LoginResponseDto;
import KT.PLIP.auth.dto.SignupRequestDto;
import KT.PLIP.auth.dto.SignupResponseDto;
import KT.PLIP.user.domain.User;
import KT.PLIP.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    
    private final UserRepository userRepository;
    
    // 회원가입
    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        // 중복 검사
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        }
        
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        
        // 사용자 생성
        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());  // 평문 저장
        user.setNickname(requestDto.getNickname());
        
        User savedUser = userRepository.save(user);
        
        // 응답 생성
        SignupResponseDto response = new SignupResponseDto();
        response.setMessage("회원가입이 완료되었습니다.");
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setNickname(savedUser.getNickname());
        
        return response;
    }
    
    // 로그인 (간단한 평문 비교)
    public LoginResponseDto login(LoginRequestDto requestDto) {
        // username으로 사용자 찾기
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        
        // password 평문 비교
        if (!requestDto.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        
        // 로그인 성공 - 사용자 정보 반환
        LoginResponseDto response = new LoginResponseDto();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setNickname(user.getNickname());
        
        return response;
    }
}
