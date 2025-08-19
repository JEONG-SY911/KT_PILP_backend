package KT.PLIP.population.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeoulDistrictResponseDto {
    private String districtName;        // 구 이름 (예: 강남구, 서초구)
    private String districtCode;        // 구 코드 (예: 11680, 11690)
    private String districtNameEng;     // 영문 이름 (예: Gangnam-gu, Seocho-gu)
    private boolean isImplemented;      // 현재 구현 여부 (강남구만 true)
    private String description;         // 구 설명
}
