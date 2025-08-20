package KT.PLIP.population.controller;

import KT.PLIP.population.dto.*;
import KT.PLIP.population.service.PopulationDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/population/ai")
@RequiredArgsConstructor
public class AiPopulationController {
    private final PopulationDetailService populationDetailService;

    // GET /population/ai/bundle/{adstrdCode}?date=YYYYMMDD
    @GetMapping("/bundle/{adstrdCode}")
    public ResponseEntity<AiPopulationBundleDto> getBundle(
            @PathVariable String adstrdCode,
            @RequestParam(required = false) String date
    ) {
        // 기본 데이터 수집
        DailyPopulationDto daily = populationDetailService.getDailyPopulation(adstrdCode);
        GenderAgePopulationDto genderAge = populationDetailService.getGenderAgePopulation(adstrdCode);
        TimeWeekPopulationDto timeWeek = populationDetailService.getTimeWeekPopulation(adstrdCode);
        DayNightPopulationDto dayNightDto = populationDetailService.getDayNightPopulation(adstrdCode);
        WeekdayWeekendPopulationDto weekdayWeekendDto = populationDetailService.getWeekdayWeekendPopulation(adstrdCode);
        DailyStatsDto dailyStatsDto = populationDetailService.getDailyStats(adstrdCode);

        // 인구 합계 계산 (선택 날짜가 있으면 해당 날짜만 집계)
        double local = 0.0, tempF = 0.0, longF = 0.0, total = 0.0;
        List<AiPopulationBundleDto.TimeStat> timeStats = new ArrayList<>();
        Set<String> dates = new HashSet<>();

        for (DailyPopulationDto.DailyData d : daily.getDailyDataList()) {
            if (date != null && !date.equals(d.getDate())) continue;
            local += Optional.ofNullable(d.getLocalPopulation()).orElse(0.0);
            tempF += Optional.ofNullable(d.getTempForeignerPopulation()).orElse(0.0);
            longF += Optional.ofNullable(d.getLongForeignerPopulation()).orElse(0.0);
            total += Optional.ofNullable(d.getTotalPopulation()).orElse(0.0);
            dates.add(d.getDate());
        }

        for (DailyPopulationDto.DailyData d : daily.getDailyDataList()) {
            if (date != null && !date.equals(d.getDate())) continue;
            AiPopulationBundleDto.TimeStat ts = new AiPopulationBundleDto.TimeStat();
            ts.setDate(d.getDate());
            ts.setTmzonPdSe(d.getTmzonPdSe());
            ts.setTimeRange(d.getTimeRange());
            ts.setTotalPopulation(Optional.ofNullable(d.getTotalPopulation()).orElse(0.0));
            ts.setLocalPopulation(Optional.ofNullable(d.getLocalPopulation()).orElse(0.0));
            ts.setTempForeignerPopulation(Optional.ofNullable(d.getTempForeignerPopulation()).orElse(0.0));
            ts.setLongForeignerPopulation(Optional.ofNullable(d.getLongForeignerPopulation()).orElse(0.0));
            timeStats.add(ts);
        }

        // 성별 합계
        AiPopulationBundleDto.GenderStats gender = new AiPopulationBundleDto.GenderStats();
        gender.setMale(Optional.ofNullable(genderAge.getMalePopulation()).orElse(0.0));
        gender.setFemale(Optional.ofNullable(genderAge.getFemalePopulation()).orElse(0.0));
        gender.setTotal(gender.getMale() + gender.getFemale());

        // 연령 합계 맵 (남+여 합산)
        Map<String, Double> ageStats = new LinkedHashMap<>();
        if (genderAge.getMaleAgeGroup() != null) {
            for (Map.Entry<String, Double> e : genderAge.getMaleAgeGroup().entrySet()) {
                ageStats.merge(e.getKey(), Optional.ofNullable(e.getValue()).orElse(0.0), Double::sum);
            }
        }
        if (genderAge.getFemaleAgeGroup() != null) {
            for (Map.Entry<String, Double> e : genderAge.getFemaleAgeGroup().entrySet()) {
                ageStats.merge(e.getKey(), Optional.ofNullable(e.getValue()).orElse(0.0), Double::sum);
            }
        }

        // 번들 DTO 조립
        AiPopulationBundleDto bundle = new AiPopulationBundleDto();
        bundle.setAdstrdCodeSe(adstrdCode);
        bundle.setDate(date);
        bundle.setGeneratedAt(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        bundle.setTimeStats(timeStats);
        bundle.setGenderStats(gender);
        bundle.setAgeStats(ageStats);
        // 원시 일별 데이터(필터 적용)
        List<DailyPopulationDto.DailyData> raw = new ArrayList<>();
        for (DailyPopulationDto.DailyData d : daily.getDailyDataList()) {
            if (date != null && !date.equals(d.getDate())) continue;
            raw.add(d);
        }
        bundle.setDaily(raw);

        // 주간/야간
        AiPopulationBundleDto.DayNight dn = new AiPopulationBundleDto.DayNight();
        dn.setDayPopulation(Optional.ofNullable(dayNightDto.getDayPopulation()).orElse(0.0));
        dn.setNightPopulation(Optional.ofNullable(dayNightDto.getNightPopulation()).orElse(0.0));
        dn.setDayNightRatio(Optional.ofNullable(dayNightDto.getDayNightRatio()).orElse(0.0));
        bundle.setDayNight(dn);

        // 시간/요일 집계
        AiPopulationBundleDto.TimeWeekAggregates tw = new AiPopulationBundleDto.TimeWeekAggregates();
        tw.setTimeSlotPopulation(timeWeek.getTimeSlotPopulation());
        tw.setWeekdayPopulation(timeWeek.getWeekdayPopulation());
        bundle.setTimeWeek(tw);

        // 주중/주말 집계
        AiPopulationBundleDto.WeekdayWeekend ww = new AiPopulationBundleDto.WeekdayWeekend();
        ww.setWeekdayPopulation(Optional.ofNullable(weekdayWeekendDto.getWeekdayPopulation()).orElse(0.0));
        ww.setWeekendPopulation(Optional.ofNullable(weekdayWeekendDto.getWeekendPopulation()).orElse(0.0));
        if (ww.getWeekendPopulation() != null && ww.getWeekendPopulation() > 0) {
            ww.setWeekdayWeekendRatio(ww.getWeekdayPopulation() / ww.getWeekendPopulation());
        } else {
            ww.setWeekdayWeekendRatio(0.0);
        }
        bundle.setWeekdayWeekend(ww);

        // 일일 통계
        AiPopulationBundleDto.DailyStats ds = new AiPopulationBundleDto.DailyStats();
        ds.setAveragePopulation(Optional.ofNullable(dailyStatsDto.getAveragePopulation()).orElse(0));
        ds.setMaxPopulation(Optional.ofNullable(dailyStatsDto.getMaxPopulation()).orElse(0));
        ds.setMinPopulation(Optional.ofNullable(dailyStatsDto.getMinPopulation()).orElse(0));
        // 최대/최소 시간대 판단 (필터된 timeStats 기준)
        if (!timeStats.isEmpty()) {
            AiPopulationBundleDto.TimeStat maxTs = timeStats.stream()
                    .max(Comparator.comparing(ts -> Optional.ofNullable(ts.getTotalPopulation()).orElse(0.0)))
                    .orElse(null);
            AiPopulationBundleDto.TimeStat minTs = timeStats.stream()
                    .min(Comparator.comparing(ts -> Optional.ofNullable(ts.getTotalPopulation()).orElse(0.0)))
                    .orElse(null);
            if (maxTs != null) ds.setMaxPopulationTime(maxTs.getTimeRange());
            if (minTs != null) ds.setMinPopulationTime(minTs.getTimeRange());
        }
        bundle.setDailyStats(ds);

        AiPopulationBundleDto.PopulationData pd = new AiPopulationBundleDto.PopulationData();
        pd.setLocal(local);
        pd.setTempForeigner(tempF);
        pd.setLongForeigner(longF);
        pd.setTotal(total);
        bundle.setPopulationData(pd);

        // 성별/연령별 상세 데이터 추가
        GenderAgePopulationDto genderAgePopulation = populationDetailService.getGenderAgePopulation(adstrdCode);
        bundle.setGenderAgePopulation(genderAgePopulation);

        return ResponseEntity.ok(bundle);
    }
}
