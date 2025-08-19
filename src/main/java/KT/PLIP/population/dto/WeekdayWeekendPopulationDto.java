package KT.PLIP.population.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class WeekdayWeekendPopulationDto {
    private Integer weekdayPopulation;
    private Integer weekendPopulation;
    private Map<String, Integer> ageGroupWeekday; // 연령대별 주중 인구
    private Map<String, Integer> ageGroupWeekend; // 연령대별 주말 인구
}
