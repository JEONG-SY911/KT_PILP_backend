package KT.PLIP.population.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TimeWeekPopulationDto {
    private Map<String, Double> timeSlotPopulation; // 시간대별 인구
    private Map<String, Double> weekdayPopulation; // 요일별 인구
}
