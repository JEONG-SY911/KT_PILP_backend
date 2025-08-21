package KT.PLIP.population.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopulationChangeDto {
    private String adstrdCodeSe;
    private String dongName;
    private String currentDate;
    private String previousDate;
    
    // 현재 인구
    private Double currentTotalPopulation;
    
    // 이전 인구
    private Double previousTotalPopulation;
    
    // 증감률 (%)
    private Double totalPopulationChangeRate;
    
    // 증감 수
    private Double totalPopulationChange;
}
