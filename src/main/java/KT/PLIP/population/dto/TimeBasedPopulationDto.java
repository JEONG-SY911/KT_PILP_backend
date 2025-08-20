package KT.PLIP.population.dto;

import lombok.Data;
import java.util.List;

@Data
public class TimeBasedPopulationDto {
    private List<TimeData> timeDataList;
    
    @Data
    public static class TimeData {
        private String date;
        private String timeZone;        // 시간대 (새벽, 오전, 오후, 저녁, 밤)
        private String timeRange;       // 시간 범위 (예: "00:00-01:00")
        private String tmzonPdSe;       // 원본 시간대 코드
        private Double totalPopulation;
        private Double localPopulation;
        private Double tempForeignerPopulation;
        private Double longForeignerPopulation;
    }
}
