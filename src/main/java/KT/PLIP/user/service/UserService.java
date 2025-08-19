package KT.PLIP.user.service;

import KT.PLIP.user.domain.User;
import KT.PLIP.user.dto.UserSignupRequestDto;
import KT.PLIP.user.dto.UserLoginRequestDto;
import KT.PLIP.user.dto.UserResponseDto;
import KT.PLIP.user.repository.UserRepository;
import KT.PLIP.favorite.domain.FavoriteLocation;
import KT.PLIP.favorite.dto.FavoriteLocationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    
    // 회원가입
    @Transactional
    public UserResponseDto signup(UserSignupRequestDto requestDto) {
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
        return convertToDto(savedUser);
    }
    
    // 로그인
    public UserResponseDto login(UserLoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        
        // 비밀번호 검증 (평문 비교)
        if (!requestDto.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        
        return convertToDto(user);
    }
    
    // 사용자 정보 조회
    public UserResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return convertToDto(user);
    }
    
    // 사용자명으로 사용자 조회
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    // 프로필 수정
    @Transactional
    public UserResponseDto updateUserProfile(Long userId, UserResponseDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        
        // 닉네임 업데이트
        if (requestDto.getNickname() != null) {
            user.setNickname(requestDto.getNickname());
        }
        
        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }
    
    // 사용자 즐겨찾기 목록 조회
    public List<FavoriteLocationResponseDto> getUserFavorites(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        
        return user.getFavoriteLocations().stream()
                .map(this::convertToFavoriteDto)
                .collect(Collectors.toList());
    }
    
    // Entity를 DTO로 변환
    private UserResponseDto convertToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setNickname(user.getNickname());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
    
    // FavoriteLocation을 DTO로 변환
    private FavoriteLocationResponseDto convertToFavoriteDto(FavoriteLocation favoriteLocation) {
        FavoriteLocationResponseDto dto = new FavoriteLocationResponseDto();
        dto.setId(favoriteLocation.getId());
        dto.setAdstrdCodeSe(favoriteLocation.getAdstrdCodeSe());
        dto.setDongName(favoriteLocation.getDongName());
        dto.setCreatedAt(favoriteLocation.getCreatedAt());
        return dto;
    }
}
