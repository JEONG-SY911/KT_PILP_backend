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
    
    // 강남구 동 목록 조회 (지도 표시용)
    @GetMapping("/gangnam/dongs")
    public ResponseEntity<List<GangnamPopulationResponseDto>> getAllDongs() {
        List<GangnamPopulationResponseDto> response = populationDetailService.getAllDongs();
        return ResponseEntity.ok(response);
    }
    
    // 특정 동의 일별 생활인구 현황 조회
    @GetMapping("/gangnam/dongs/{adstrdCode}/daily")
    public ResponseEntity<DailyPopulationDto> getDailyPopulation(
            @PathVariable String adstrdCode) {
        DailyPopulationDto response = populationDetailService.getDailyPopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 특정 동의 시간대별 생활인구 현황 조회
    @GetMapping("/gangnam/dongs/{adstrdCode}/time-based")
    public ResponseEntity<TimeBasedPopulationDto> getTimeBasedPopulation(
            @PathVariable String adstrdCode) {
        TimeBasedPopulationDto response = populationDetailService.getTimeBasedPopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 특정 동의 일일 통계 조회
    @GetMapping("/gangnam/dongs/{adstrdCode}/stats/daily")
    public ResponseEntity<DailyStatsDto> getDailyStats(
            @PathVariable String adstrdCode) {
        DailyStatsDto response = populationDetailService.getDailyStats(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 특정 동의 주간/야간 통계 조회
    @GetMapping("/gangnam/dongs/{adstrdCode}/stats/day-night")
    public ResponseEntity<DayNightPopulationDto> getDayNightStats(
            @PathVariable String adstrdCode) {
        DayNightPopulationDto response = populationDetailService.getDayNightPopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 특정 동의 시간대/요일별 통계 조회
    @GetMapping("/gangnam/dongs/{adstrdCode}/stats/time-week")
    public ResponseEntity<TimeWeekPopulationDto> getTimeWeekStats(
            @PathVariable String adstrdCode) {
        TimeWeekPopulationDto response = populationDetailService.getTimeWeekPopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 특정 동의 주중/주말 통계 조회
    @GetMapping("/gangnam/dongs/{adstrdCode}/stats/weekday-weekend")
    public ResponseEntity<WeekdayWeekendPopulationDto> getWeekdayWeekendStats(
            @PathVariable String adstrdCode) {
        WeekdayWeekendPopulationDto response = populationDetailService.getWeekdayWeekendPopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 특정 동의 성별/연령별 통계 조회
    @GetMapping("/gangnam/dongs/{adstrdCode}/stats/gender-age")
    public ResponseEntity<GenderAgePopulationDto> getGenderAgeStats(
            @PathVariable String adstrdCode) {
        GenderAgePopulationDto response = populationDetailService.getGenderAgePopulation(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 동 이름으로 일별 생활인구 현황 조회
    @GetMapping("/gangnam/dongs/name/{dongName}/daily")
    public ResponseEntity<DailyPopulationDto> getDailyPopulationByDongName(
            @PathVariable String dongName) {
        DailyPopulationDto response = populationDetailService.getDailyPopulationByDongName(dongName);
        return ResponseEntity.ok(response);
    }
}
