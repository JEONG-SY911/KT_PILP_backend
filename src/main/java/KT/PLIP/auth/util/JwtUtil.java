package KT.PLIP.auth.util;

import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    
    // 간단한 시크릿 키 (실제 배포시에는 환경변수로 관리)
    private static final String SECRET_KEY = "mySecretKey123456789012345678901234567890";
    
    // 토큰 만료시간 (24시간)
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;
    
    // 간단한 토큰 생성 (실제 JWT 라이브러리 대신 Base64 사용)
    public String generateToken(String username, Long userId) {
        long now = System.currentTimeMillis();
        long expiry = now + EXPIRATION_TIME;
        
        // 간단한 토큰 구조: username|userId|expiry
        String tokenData = username + "|" + userId + "|" + expiry;
        return Base64.getEncoder().encodeToString(tokenData.getBytes());
    }
    
    // 토큰에서 사용자명 추출
    public String getUsernameFromToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            String[] parts = decoded.split("\\|");
            if (parts.length >= 3) {
                long expiry = Long.parseLong(parts[2]);
                if (System.currentTimeMillis() < expiry) {
                    return parts[0];
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    // 토큰에서 사용자 ID 추출
    public Long getUserIdFromToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            String[] parts = decoded.split("\\|");
            if (parts.length >= 3) {
                long expiry = Long.parseLong(parts[2]);
                if (System.currentTimeMillis() < expiry) {
                    return Long.parseLong(parts[1]);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            String[] parts = decoded.split("\\|");
            if (parts.length >= 3) {
                long expiry = Long.parseLong(parts[2]);
                return System.currentTimeMillis() < expiry;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
