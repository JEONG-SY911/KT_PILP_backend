package KT.PLIP.population.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AiPopulationBundleDto {
	private String dongName;
	private String adstrdCodeSe;
	private String date;        // 필터된 날짜(선택)
	private String generatedAt; // 응답 생성 시각(ISO)

	private PopulationData populationData;
	private List<TimeStat> timeStats;
	private GenderStats genderStats;
	private Map<String, Double> ageStats; // (남+여) 합계 연령대 지도
	private List<DailyPopulationDto.DailyData> daily; // 원시 일별 데이터(시간대 포함)
	private DayNight dayNight;                 // 주간/야간 집계
	private TimeWeekAggregates timeWeek;       // 시간대/요일 집계
	private WeekdayWeekend weekdayWeekend;     // 주중/주말 집계
	private DailyStats dailyStats;             // 일일 통계(평균/최대/최소)
	private GenderAgePopulationDto genderAgePopulation; // 성별 연령 분포(선택)

	@Getter
	@Setter
	public static class PopulationData {
		private Double total;
		private Double local;
		private Double longForeigner;
		private Double tempForeigner;
	}

	@Getter
	@Setter
	public static class TimeStat {
		private String date;
		private String timeRange;
		private String tmzonPdSe;
		private Double totalPopulation;
		private Double localPopulation;
		private Double longForeignerPopulation;
		private Double tempForeignerPopulation;
		private Double malePopulation;
		private Double femalePopulation;
	}

	@Getter
	@Setter
	public static class GenderStats {
		private Double male;
		private Double female;
		private Double total;
	}

	@Getter
	@Setter
	public static class DayNight {
		private Double dayPopulation;
		private Double nightPopulation;
		private Double dayNightRatio;
	}

	@Getter
	@Setter
	public static class TimeWeekAggregates {
		private Map<String, Double> timeSlotPopulation;
		private Map<String, Double> weekdayPopulation;
	}

	@Getter
	@Setter
	public static class WeekdayWeekend {
		private Double weekdayPopulation;
		private Double weekendPopulation;
		private Double weekdayWeekendRatio;
	}

	@Getter
	@Setter
	public static class DailyStats {
		private Integer averagePopulation;
		private Integer maxPopulation;
		private Integer minPopulation;
		private String maxPopulationTime;
		private String minPopulationTime;
	}

	@Getter
	@Setter
	public static class GenderAgePopulation {
		private Double malePopulation;
		private Double femalePopulation;
		private Map<String, Double> maleAgeGroup; // 남성 연령대별 인구
		private Map<String, Double> femaleAgeGroup; // 여성 연령대별 인구
	}
}
