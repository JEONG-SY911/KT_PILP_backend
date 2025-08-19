package KT.PLIP.population.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LongForeignerResponseDto {
    private Long id;
    private Integer totLvpopCo;
    private Double chinaStaypopCo;
    private Double otherStaypopCo;
    private String oaCd;
    private String stdrDeId;
    private String tmzonPdSe;
    private String adstrdCodeSe;
    
    // 시간대 정보 (사람이 읽기 쉬운 형태)
    private String timeZone;
    private String timeRange;
}
