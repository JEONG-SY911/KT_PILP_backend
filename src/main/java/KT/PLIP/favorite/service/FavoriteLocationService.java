package KT.PLIP.favorite.service;

import KT.PLIP.favorite.domain.FavoriteLocation;
import KT.PLIP.user.domain.User;
import KT.PLIP.favorite.dto.FavoriteLocationRequestDto;
import KT.PLIP.favorite.dto.FavoriteLocationResponseDto;
import KT.PLIP.favorite.repository.FavoriteLocationRepository;
import KT.PLIP.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteLocationService {
    
    private final FavoriteLocationRepository favoriteLocationRepository;
    private final UserRepository userRepository;
    
    // 즐겨찾기 지역 추가
    @Transactional
    public FavoriteLocationResponseDto addFavoriteLocation(Long userId, FavoriteLocationRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        
        // 이미 즐겨찾기에 추가된 지역인지 확인
        if (favoriteLocationRepository.existsByUserIdAndAdstrdCodeSe(userId, requestDto.getAdstrdCodeSe())) {
            throw new RuntimeException("이미 즐겨찾기에 추가된 지역입니다.");
        }
        
        FavoriteLocation favoriteLocation = new FavoriteLocation();
        favoriteLocation.setUser(user);
        favoriteLocation.setAdstrdCodeSe(requestDto.getAdstrdCodeSe());
        favoriteLocation.setDongName(requestDto.getDongName());
        
        FavoriteLocation savedLocation = favoriteLocationRepository.save(favoriteLocation);
        return convertToDto(savedLocation);
    }
    
    // 즐겨찾기 지역 목록 조회
    public List<FavoriteLocationResponseDto> getFavoriteLocations(Long userId) {
        List<FavoriteLocation> favoriteLocations = favoriteLocationRepository.findByUserId(userId);
        return favoriteLocations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // 즐겨찾기 상태 확인
    public boolean isFavoriteLocation(Long userId, String adstrdCodeSe) {
        return favoriteLocationRepository.existsByUserIdAndAdstrdCodeSe(userId, adstrdCodeSe);
    }
    
    // 즐겨찾기 지역 삭제
    @Transactional
    public void removeFavoriteLocation(Long userId, String adstrdCodeSe) {
        FavoriteLocation favoriteLocation = favoriteLocationRepository
                .findByUserIdAndAdstrdCodeSe(userId, adstrdCodeSe)
                .orElseThrow(() -> new RuntimeException("즐겨찾기 지역을 찾을 수 없습니다."));
        
        favoriteLocationRepository.delete(favoriteLocation);
    }
    
    // Entity를 DTO로 변환
    private FavoriteLocationResponseDto convertToDto(FavoriteLocation favoriteLocation) {
        FavoriteLocationResponseDto dto = new FavoriteLocationResponseDto();
        dto.setId(favoriteLocation.getId());
        dto.setAdstrdCodeSe(favoriteLocation.getAdstrdCodeSe());
        dto.setDongName(favoriteLocation.getDongName());
        dto.setCreatedAt(favoriteLocation.getCreatedAt());
        return dto;
    }
}
