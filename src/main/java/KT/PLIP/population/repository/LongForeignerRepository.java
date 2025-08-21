package KT.PLIP.population.repository;

import KT.PLIP.population.domain.LongForeigner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LongForeignerRepository extends JpaRepository<LongForeigner, Long> {
    
    List<LongForeigner> findByAdstrdCodeSe(String adstrdCodeSe);
    
    // 행정동 코드와 날짜로 조회
    List<LongForeigner> findByAdstrdCodeSeAndStdrDeId(String adstrdCodeSe, String stdrDeId);
    
    @Query("SELECT lf FROM LongForeigner lf WHERE lf.adstrdCodeSe IN :codes")
    List<LongForeigner> findByAdstrdCodes(@Param("codes") List<String> adstrdCodes);
}
