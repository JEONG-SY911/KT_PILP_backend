package KT.PLIP.population.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class GenderAgePopulationDto {
    private Integer malePopulation;
    private Integer femalePopulation;
    private Map<String, Integer> maleAgeGroup; // 남성 연령대별 인구
    private Map<String, Integer> femaleAgeGroup; // 여성 연령대별 인구
}
