package KT.PLIP.population.repository;

import KT.PLIP.population.domain.SeoulDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeoulDistrictRepository extends JpaRepository<SeoulDistrict, Long> {
    
    // 활성화된 모든 구 조회 (정렬 순서대로)
    @Query("SELECT sd FROM SeoulDistrict sd WHERE sd.isActive = true ORDER BY sd.sortOrder ASC, sd.districtName ASC")
    List<SeoulDistrict> findAllActiveOrderBySortOrder();
    
    // 구 코드로 조회
    Optional<SeoulDistrict> findByDistrictCode(String districtCode);
    
    // 구 이름으로 조회
    Optional<SeoulDistrict> findByDistrictName(String districtName);
    
    // 구 코드 존재 여부 확인
    boolean existsByDistrictCode(String districtCode);
    
    // 구 이름 존재 여부 확인
    boolean existsByDistrictName(String districtName);
}
