package KT.PLIP.population.repository;

import KT.PLIP.population.domain.TempForeigner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempForeignerRepository extends JpaRepository<TempForeigner, Long> {
    
    List<TempForeigner> findByAdstrdCodeSe(String adstrdCodeSe);
    
    // 행정동 코드와 날짜로 조회
    List<TempForeigner> findByAdstrdCodeSeAndStdrDeId(String adstrdCodeSe, String stdrDeId);
    
    @Query("SELECT tf FROM TempForeigner tf WHERE tf.adstrdCodeSe IN :codes")
    List<TempForeigner> findByAdstrdCodes(@Param("codes") List<String> adstrdCodes);
}
