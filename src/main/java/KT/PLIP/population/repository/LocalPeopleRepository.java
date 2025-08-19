package KT.PLIP.population.repository;

import KT.PLIP.population.domain.LocalPeople;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalPeopleRepository extends JpaRepository<LocalPeople, Long> {
    
    // 행정동 코드로 조회
    List<LocalPeople> findByAdstrdCodeSe(String adstrdCodeSe);
    
    // 전체 인구가 특정 값 이상인 데이터 조회
    List<LocalPeople> findByTotLvpopCoGreaterThan(Integer totLvpopCo);
    
    // 복잡한 쿼리를 위한 @Query 사용
    @Query("SELECT lp FROM LocalPeople lp WHERE lp.adstrdCodeSe IN :codes")
    List<LocalPeople> findByAdstrdCodes(@Param("codes") List<String> adstrdCodes);
}
