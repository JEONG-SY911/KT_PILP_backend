package KT.PLIP.favorite.repository;

import KT.PLIP.favorite.domain.FavoriteLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteLocationRepository extends JpaRepository<FavoriteLocation, Long> {
    
    List<FavoriteLocation> findByUserId(Long userId);
    
    Optional<FavoriteLocation> findByUserIdAndAdstrdCodeSe(Long userId, String adstrdCodeSe);
    
    boolean existsByUserIdAndAdstrdCodeSe(Long userId, String adstrdCodeSe);
}
