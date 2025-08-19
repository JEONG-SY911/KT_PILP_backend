package KT.PLIP.population.repository;

import KT.PLIP.population.domain.GangnamPopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GangnamPopulationRepository extends JpaRepository<GangnamPopulation, Long> {
    
    List<GangnamPopulation> findAll();
    
    Optional<GangnamPopulation> findByDongName(String dongName);
    
    Optional<GangnamPopulation> findByAdstrdCodeSe(String adstrdCodeSe);
}
