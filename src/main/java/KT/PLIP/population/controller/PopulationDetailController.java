package KT.PLIP.population.controller;

import KT.PLIP.population.dto.*;
import KT.PLIP.population.service.PopulationDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/population")
@RequiredArgsConstructor
public class PopulationDetailController {
    
    private final PopulationDetailService populationDetailService;
    
    // 서울 전체 구 목록 조회는 SeoulDistrictController에서 처리
    // 이 메서드는 제거됨 - /population/seoul/districts는 SeoulDistrictController에서 처리
    
    // 서울 전체 동 목록 조회 (지도 표시용)
    @GetMapping("/{district}/dongs")
    public ResponseEntity<List<SeoulPopulationResponseDto>> getAllDongs() {
        List<SeoulPopulationResponseDto> response = populationDetailService.getAllSeoulDongs();
        return ResponseEntity.ok(response);
    }
    
    // 특정 동의 상세 생활인구 현황 조회
    @GetMapping("/{district}/dongs/{adstrdCode}")
    public ResponseEntity<PopulationDetailResponseDto> getPopulationDetail(
            @PathVariable String adstrdCode) {
        PopulationDetailResponseDto response = populationDetailService.getPopulationDetail(adstrdCode);
        return ResponseEntity.ok(response);
    }
    

    // 일별 데이터 (시간대 정보 포함)
    @GetMapping("/{district}/dongs/{adstrdCode}/daily")
    public ResponseEntity<DailyPopulationDto> getDailyPopulation(
            @PathVariable String adstrdCode) {
        DailyPopulationDto response = populationDetailService.getDailyPopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    


    
    // 일일 통계 (평균/최대/최소)
    @GetMapping("/{district}/dongs/{adstrdCode}/stats/daily")
    public ResponseEntity<DailyStatsDto> getDailyStats(
            @PathVariable String adstrdCode) {
        DailyStatsDto response = populationDetailService.getDailyStats(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 주간/야간 통계
    @GetMapping("/{district}/dongs/{adstrdCode}/stats/day-night")
    public ResponseEntity<DayNightPopulationDto> getDayNightPopulation(
            @PathVariable String adstrdCode) {
        DayNightPopulationDto response = populationDetailService.getDayNightPopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 시간대/요일별 통계
    @GetMapping("/{district}/dongs/{adstrdCode}/stats/time-week")
    public ResponseEntity<TimeWeekPopulationDto> getTimeWeekPopulation(
            @PathVariable String adstrdCode) {
        TimeWeekPopulationDto response = populationDetailService.getTimeWeekPopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 주중/주말 통계
    @GetMapping("/{district}/dongs/{adstrdCode}/stats/weekday-weekend")
    public ResponseEntity<WeekdayWeekendPopulationDto> getWeekdayWeekendPopulation(
            @PathVariable String adstrdCode) {
        WeekdayWeekendPopulationDto response = populationDetailService.getWeekdayWeekendPopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 성별/연령별 통계
    @GetMapping("/{district}/dongs/{adstrdCode}/stats/gender-age")
    public ResponseEntity<GenderAgePopulationDto> getGenderAgePopulation(
            @PathVariable String adstrdCode) {
        GenderAgePopulationDto response = populationDetailService.getGenderAgePopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 성별별 총 인구수
    @GetMapping("/{district}/dongs/{adstrdCode}/stats/gender")
    public ResponseEntity<GenderPopulationDto> getGenderPopulation(
            @PathVariable String adstrdCode) {
        GenderPopulationDto response = populationDetailService.getGenderPopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // // 성별별 연령대 분포
    // @GetMapping("/gangnam/dongs/{adstrdCode}/stats/gender-age-distribution")
    // public ResponseEntity<GenderAgePopulationDto> getGenderAgePopulation(
    //         @PathVariable String adstrdCode) {
    //             GenderAgePopulationDto response = populationDetailService.getGenderAgePopulation(adstrdCode);
    //     return ResponseEntity.ok(response);
    // }
    
    // 성별별 시간대 분포 - 하위 호환성
    @GetMapping("/gangnam/dongs/{adstrdCode}/stats/gender-time")
    public ResponseEntity<GenderTimeDistributionDto> getGangnamGenderTimeDistribution(
            @PathVariable String adstrdCode) {
        GenderTimeDistributionDto response = populationDetailService.getGenderTimeDistribution(adstrdCode);
        return ResponseEntity.ok(response);
    }
}
