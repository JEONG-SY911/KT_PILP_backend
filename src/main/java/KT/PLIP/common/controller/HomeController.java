package KT.PLIP.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "PLIP Population API Server");
        response.put("version", "1.0.0");
        response.put("status", "running");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("서울 전체 구 목록", "GET /population/seoul/districts");
        endpoints.put("구 정보 조회", "GET /population/seoul/districts/{districtCode}");
        endpoints.put("구 추가", "POST /population/seoul/districts");
        endpoints.put("구 수정", "PUT /population/seoul/districts/{districtCode}");
        endpoints.put("구 비활성화", "DELETE /population/seoul/districts/{districtCode}");
        endpoints.put("강남구 동 목록 조회", "GET /population/gangnam/dongs");
        endpoints.put("동 이름으로 상세 조회", "GET /population/gangnam/dongs/name/{dongName}");
        endpoints.put("행정동 코드로 상세 조회", "GET /population/gangnam/dongs/{adstrdCode}");
        endpoints.put("회원가입", "POST /auth/signup");
        endpoints.put("로그인", "POST /auth/login");
        endpoints.put("사용자 정보 조회 (보호됨)", "GET /api/users/{userId}");
        endpoints.put("내 정보 조회 (보호됨)", "GET /api/users/me");
        endpoints.put("즐겨찾기 추가", "POST /api/favorites/{userId}");
        endpoints.put("즐겨찾기 목록", "GET /api/favorites/{userId}");
        endpoints.put("즐겨찾기 삭제", "DELETE /api/favorites/{userId}/{adstrdCode}");
        
        response.put("availableEndpoints", endpoints);
        
        return response;
    }
    
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return response;
    }
}
