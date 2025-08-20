package KT.PLIP.population.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class GenderAgePopulationDto {
    private Double malePopulation;
    private Double femalePopulation;
    private Map<String, Double> maleAgeGroup; // 남성 연령대별 인구
    private Map<String, Double> femaleAgeGroup; // 여성 연령대별 인구
}
