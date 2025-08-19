package KT.PLIP.favorite.controller;

import KT.PLIP.favorite.dto.FavoriteLocationRequestDto;
import KT.PLIP.favorite.dto.FavoriteLocationResponseDto;
import KT.PLIP.favorite.service.FavoriteLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteLocationController {
    
    private final FavoriteLocationService favoriteLocationService;
    
    // 즐겨찾기 지역 추가 (하트 클릭)
    @PostMapping("/add")
    public ResponseEntity<?> addFavoriteLocation(
            @RequestBody FavoriteLocationRequestDto requestDto,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of(
                "error", true,
                "message", "로그인이 필요합니다."
            ));
        }
        try {
            FavoriteLocationResponseDto response = favoriteLocationService.addFavoriteLocation(userId, requestDto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", true,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", true,
                "message", "서버 오류가 발생했습니다.",
                "detail", e.getMessage()
            ));
        }
    }
    
    // 내 즐겨찾기 지역 목록 조회 (마이페이지)
    @GetMapping("/my")
    public ResponseEntity<List<FavoriteLocationResponseDto>> getMyFavoriteLocations(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        List<FavoriteLocationResponseDto> response = favoriteLocationService.getFavoriteLocations(userId);
        return ResponseEntity.ok(response);
    }
    
    // 즐겨찾기 상태 확인 (하트 표시용)
    @GetMapping("/check/{adstrdCodeSe}")
    public ResponseEntity<Boolean> checkFavoriteStatus(
            @PathVariable String adstrdCodeSe,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.ok(false);
        }
        boolean isFavorite = favoriteLocationService.isFavoriteLocation(userId, adstrdCodeSe);
        return ResponseEntity.ok(isFavorite);
    }
    
    // 즐겨찾기 지역 삭제 (하트 해제)
    @DeleteMapping("/remove/{adstrdCodeSe}")
    public ResponseEntity<Map<String, Object>> removeFavoriteLocation(
            @PathVariable String adstrdCodeSe,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        favoriteLocationService.removeFavoriteLocation(userId, adstrdCodeSe);
        return ResponseEntity.ok(java.util.Map.of("success", true));
    }
}
