package KT.PLIP.population.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class WeekdayWeekendPopulationDto {
    private Double weekdayPopulation;
    private Double weekendPopulation;
    private Map<String, Double> ageGroupWeekday; // 연령대별 주중 인구
    private Map<String, Double> ageGroupWeekend; // 연령대별 주말 인구
}
