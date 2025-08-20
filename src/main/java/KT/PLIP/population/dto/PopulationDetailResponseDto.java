package KT.PLIP.population.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopulationDetailResponseDto {
    private String dongName;
    private String adstrdCodeSe;
    
    // 일별 생활인구 현황
    private DailyPopulationDto dailyPopulation;
    
    // 일일 평균/최대/최소 생활인구 현황
    private DailyStatsDto dailyStats;
    
    // 주간/야간 생활인구 현황
    private DayNightPopulationDto dayNightPopulation;
    
    // 시간대별/요일별 생활인구 현황
    private TimeWeekPopulationDto timeWeekPopulation;
    
    // 주중/주말/연령대별 생활인구 현황
    private WeekdayWeekendPopulationDto weekdayWeekendPopulation;
    
    // 성별/연령별 생활인구 현황
    private GenderAgePopulationDto genderAgePopulation;
}
