package KT.PLIP.user.controller;

import KT.PLIP.user.dto.UserSignupRequestDto;
import KT.PLIP.user.dto.UserLoginRequestDto;
import KT.PLIP.user.dto.UserResponseDto;
import KT.PLIP.user.dto.UserRequestDto;
import KT.PLIP.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    // 사용자 정보 조회 (보호된 엔드포인트)
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable Long userId) {
        UserResponseDto response = userService.getUserInfo(userId);
        return ResponseEntity.ok(response);
    }
    
    // 내 정보 조회 (마이페이지)
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        UserResponseDto response = userService.getUserInfo(userId);
        return ResponseEntity.ok(response);
    }
    
    // 내 프로필 수정
    @PutMapping("/me/profile")
    public ResponseEntity<UserResponseDto> updateMyProfile(@RequestBody UserRequestDto requestDto, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        UserResponseDto response = userService.updateUserProfile(userId, requestDto);
        return ResponseEntity.ok(response);
    }

    // 내 프로필 삭제
    @DeleteMapping("/me/profile")
    public ResponseEntity<Void> deleteMyProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        userService.deleteMyProfile(userId);
        return ResponseEntity.noContent().build();
    }

    // 내 즐겨찾기 지역 목록 조회
    @GetMapping("/me/favorites")
    public ResponseEntity<?> getMyFavorites(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userService.getUserFavorites(userId));
    }
}
