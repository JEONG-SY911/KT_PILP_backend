package KT.PLIP.population.service;

import KT.PLIP.population.domain.*;
import KT.PLIP.population.dto.*;
import KT.PLIP.population.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PopulationDetailService {
    
    private final LocalPeopleRepository localPeopleRepository;
    private final TempForeignerRepository tempForeignerRepository;
    private final LongForeignerRepository longForeignerRepository;
    private final GangnamPopulationRepository gangnamPopulationRepository;
    private final SeoulDistrictRepository seoulDistrictRepository;
    
    // 서울 전체 구 목록 조회
    public List<SeoulDistrictResponseDto> getAllDistricts() {
        // 데이터베이스에서 실제 구 정보 조회
        List<String> availableDistricts = getAvailableDistrictsFromDatabase();
        
        // seoul_districts 테이블에서 모든 활성화된 구 조회
        List<SeoulDistrict> seoulDistricts = seoulDistrictRepository.findAllActiveOrderBySortOrder();
        
        // 만약 seoul_districts 테이블이 비어있다면 기본 데이터 생성
        if (seoulDistricts.isEmpty()) {
            seoulDistricts = initializeDefaultDistricts();
        }
        
        // 각 구에 대해 데이터베이스에 실제 데이터가 있는지 확인하여 isImplemented 설정
        return seoulDistricts.stream()
                .map(district -> createDistrictFromEntity(district, availableDistricts.contains(district.getDistrictCode())))
                .collect(Collectors.toList());
    }
    
    // 데이터베이스에서 사용 가능한 구 코드 조회
    public List<String> getAvailableDistrictsFromDatabase() {
        // LocalPeople 테이블에서 고유한 구 코드 조회 (앞 5자리가 구 코드)
        List<LocalPeople> allLocalPeople = localPeopleRepository.findAll();
        return allLocalPeople.stream()
                .map(local -> {
                    String adstrdCode = local.getAdstrdCodeSe();
                    return adstrdCode != null && adstrdCode.length() >= 5 ? adstrdCode.substring(0, 5) : null;
                })
                .filter(code -> code != null)
                .distinct()
                .collect(Collectors.toList());
    }
    
    // 기본 구 데이터 초기화 (seoul_districts 테이블이 비어있을 때만 실행)
    private List<SeoulDistrict> initializeDefaultDistricts() {
        List<SeoulDistrict> defaultDistricts = new ArrayList<>();
        
        // 서울 25개 구 기본 정보
        defaultDistricts.add(createSeoulDistrict("11680", "강남구", "Gangnam-gu", "강남구 생활인구 현황", 1));
        defaultDistricts.add(createSeoulDistrict("11740", "강동구", "Gangdong-gu", "강동구 생활인구 현황", 2));
        defaultDistricts.add(createSeoulDistrict("11305", "강북구", "Gangbuk-gu", "강북구 생활인구 현황", 3));
        defaultDistricts.add(createSeoulDistrict("11500", "강서구", "Gangseo-gu", "강서구 생활인구 현황", 4));
        defaultDistricts.add(createSeoulDistrict("11620", "관악구", "Gwanak-gu", "관악구 생활인구 현황", 5));
        defaultDistricts.add(createSeoulDistrict("11215", "광진구", "Gwangjin-gu", "광진구 생활인구 현황", 6));
        defaultDistricts.add(createSeoulDistrict("11530", "구로구", "Guro-gu", "구로구 생활인구 현황", 7));
        defaultDistricts.add(createSeoulDistrict("11545", "금천구", "Geumcheon-gu", "금천구 생활인구 현황", 8));
        defaultDistricts.add(createSeoulDistrict("11350", "노원구", "Nowon-gu", "노원구 생활인구 현황", 9));
        defaultDistricts.add(createSeoulDistrict("11320", "도봉구", "Dobong-gu", "도봉구 생활인구 현황", 10));
        defaultDistricts.add(createSeoulDistrict("11230", "동대문구", "Dongdaemun-gu", "동대문구 생활인구 현황", 11));
        defaultDistricts.add(createSeoulDistrict("11590", "동작구", "Dongjak-gu", "동작구 생활인구 현황", 12));
        defaultDistricts.add(createSeoulDistrict("11440", "마포구", "Mapo-gu", "마포구 생활인구 현황", 13));
        defaultDistricts.add(createSeoulDistrict("11410", "서대문구", "Seodaemun-gu", "서대문구 생활인구 현황", 14));
        defaultDistricts.add(createSeoulDistrict("11690", "서초구", "Seocho-gu", "서초구 생활인구 현황", 15));
        defaultDistricts.add(createSeoulDistrict("11200", "성동구", "Seongdong-gu", "성동구 생활인구 현황", 16));
        defaultDistricts.add(createSeoulDistrict("11290", "성북구", "Seongbuk-gu", "성북구 생활인구 현황", 17));
        defaultDistricts.add(createSeoulDistrict("11710", "송파구", "Songpa-gu", "송파구 생활인구 현황", 18));
        defaultDistricts.add(createSeoulDistrict("11470", "양천구", "Yangcheon-gu", "양천구 생활인구 현황", 19));
        defaultDistricts.add(createSeoulDistrict("11560", "영등포구", "Yeongdeungpo-gu", "영등포구 생활인구 현황", 20));
        defaultDistricts.add(createSeoulDistrict("11170", "용산구", "Yongsan-gu", "용산구 생활인구 현황", 21));
        defaultDistricts.add(createSeoulDistrict("11380", "은평구", "Eunpyeong-gu", "은평구 생활인구 현황", 22));
        defaultDistricts.add(createSeoulDistrict("11110", "종로구", "Jongno-gu", "종로구 생활인구 현황", 23));
        defaultDistricts.add(createSeoulDistrict("11140", "중구", "Jung-gu", "중구 생활인구 현황", 24));
        defaultDistricts.add(createSeoulDistrict("11260", "중랑구", "Jungnang-gu", "중랑구 생활인구 현황", 25));
        
        // 데이터베이스에 저장
        return seoulDistrictRepository.saveAll(defaultDistricts);
    }
    
    // SeoulDistrict 엔티티 생성 헬퍼 메서드
    private SeoulDistrict createSeoulDistrict(String code, String name, String nameEng, String description, int sortOrder) {
        SeoulDistrict district = new SeoulDistrict();
        district.setDistrictCode(code);
        district.setDistrictName(name);
        district.setDistrictNameEng(nameEng);
        district.setDescription(description);
        district.setSortOrder(sortOrder);
        district.setActive(true);
        return district;
    }
    
    // SeoulDistrict 엔티티를 DTO로 변환
    private SeoulDistrictResponseDto createDistrictFromEntity(SeoulDistrict district, boolean isImplemented) {
        SeoulDistrictResponseDto dto = new SeoulDistrictResponseDto();
        dto.setDistrictName(district.getDistrictName());
        dto.setDistrictCode(district.getDistrictCode());
        dto.setDistrictNameEng(district.getDistrictNameEng());
        dto.setImplemented(isImplemented);
        dto.setDescription(district.getDescription());
        return dto;
    }
    
    // 기존 createDistrict 메서드 (하위 호환성을 위해 유지)
    private SeoulDistrictResponseDto createDistrict(String name, String code, String nameEng, boolean implemented, String description) {
        SeoulDistrictResponseDto district = new SeoulDistrictResponseDto();
        district.setDistrictName(name);
        district.setDistrictCode(code);
        district.setDistrictNameEng(nameEng);
        district.setImplemented(implemented);
        district.setDescription(description);
        return district;
    }
    
    // 강남구 모든 동 목록 조회
    public List<GangnamPopulationResponseDto> getAllDongs() {
        List<GangnamPopulation> gangnamList = gangnamPopulationRepository.findAll();
        return gangnamList.stream()
                .map(this::convertToGangnamDto)
                .collect(Collectors.toList());
    }
    
    // 일별 생활인구 현황
    public DailyPopulationDto getDailyPopulation(String adstrdCode) {
        System.out.println("getDailyPopulation 시작 - 행정동 코드: " + adstrdCode);
        
        DailyPopulationDto dto = new DailyPopulationDto();
        List<DailyPopulationDto.DailyData> dailyDataList = new ArrayList<>();
        
        // 실제 데이터베이스에서 조회
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSe(adstrdCode);
        List<TempForeigner> tempForeignerList = tempForeignerRepository.findByAdstrdCodeSe(adstrdCode);
        List<LongForeigner> longForeignerList = longForeignerRepository.findByAdstrdCodeSe(adstrdCode);
        
        System.out.println("조회된 데이터 개수 - 국내인: " + localPeopleList.size() + 
                          ", 단기외국인: " + tempForeignerList.size() + 
                          ", 장기외국인: " + longForeignerList.size());
        
        // 각 테이블의 데이터를 합쳐서 일별 데이터 생성
        for (LocalPeople local : localPeopleList) {
            System.out.println("국내인 데이터 처리 중 - stdrDeId: " + local.getStdrDeId() + 
                              ", tmzonPdSe: " + local.getTmzonPdSe() + 
                              ", totLvpopCo: " + local.getTotLvpopCo());
            
            DailyPopulationDto.DailyData data = new DailyPopulationDto.DailyData();
            data.setDate(local.getStdrDeId() != null ? local.getStdrDeId() : "20240101"); // null 체크
            // 요일 설정
            try {
                data.setWeekday(getWeekdayFromDate(data.getDate()));
            } catch (Exception e) {
                data.setWeekday("알 수 없음");
            }
            data.setLocalPopulation(local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0);
            
            // 시간대 정보 설정
            data.setTmzonPdSe(local.getTmzonPdSe());
            setTimeZoneInfo(data, local.getTmzonPdSe());
            
            // 해당 행정동의 단기 외국인 데이터 찾기
            TempForeigner tempForeigner = tempForeignerList.stream()
                    .filter(tf -> tf.getAdstrdCodeSe() != null && tf.getAdstrdCodeSe().equals(adstrdCode) && 
                                 tf.getStdrDeId() != null && tf.getStdrDeId().equals(local.getStdrDeId()) &&
                                 tf.getTmzonPdSe() != null && tf.getTmzonPdSe().equals(local.getTmzonPdSe()))
                    .findFirst()
                    .orElse(null);
            data.setTempForeignerPopulation(tempForeigner != null && tempForeigner.getTotLvpopCo() != null ? tempForeigner.getTotLvpopCo() : 0);
            
            System.out.println("단기외국인 매칭 결과: " + (tempForeigner != null ? "찾음" : "없음"));
            
            // 해당 행정동의 장기 외국인 데이터 찾기
            LongForeigner longForeigner = longForeignerList.stream()
                    .filter(lf -> lf.getAdstrdCodeSe() != null && lf.getAdstrdCodeSe().equals(adstrdCode) && 
                                 lf.getStdrDeId() != null && lf.getStdrDeId().equals(local.getStdrDeId()) &&
                                 lf.getTmzonPdSe() != null && lf.getTmzonPdSe().equals(local.getTmzonPdSe()))
                    .findFirst()
                    .orElse(null);
            data.setLongForeignerPopulation(longForeigner != null && longForeigner.getTotLvpopCo() != null ? longForeigner.getTotLvpopCo() : 0);
            
            System.out.println("장기외국인 매칭 결과: " + (longForeigner != null ? "찾음" : "없음"));
            
            // 총 인구 계산
            data.setTotalPopulation(
                data.getLocalPopulation() + 
                data.getTempForeignerPopulation() + 
                data.getLongForeignerPopulation()
            );
            
            System.out.println("일별 데이터 생성 완료 - 날짜: " + data.getDate() + 
                              ", 시간: " + data.getTimeZone() + " (" + data.getTimeRange() + ")" +
                              ", 총인구: " + data.getTotalPopulation());
            
            dailyDataList.add(data);
        }
        
        System.out.println("getDailyPopulation 완료 - 생성된 일별 데이터 개수: " + dailyDataList.size());
        dto.setDailyDataList(dailyDataList);
        return dto;
    }
    
    // 시간대별 생활인구 현황
    public TimeBasedPopulationDto getTimeBasedPopulation(String adstrdCode) {
        // 일별 데이터를 그대로 시간대별 데이터로 사용 (이미 시간대 정보가 포함되어 있음)
        DailyPopulationDto dailyData = getDailyPopulation(adstrdCode);
        TimeBasedPopulationDto timeBasedDto = new TimeBasedPopulationDto();
        List<TimeBasedPopulationDto.TimeData> timeDataList = new ArrayList<>();
        
        for (DailyPopulationDto.DailyData daily : dailyData.getDailyDataList()) {
            TimeBasedPopulationDto.TimeData timeData = new TimeBasedPopulationDto.TimeData();
            timeData.setDate(daily.getDate());
            timeData.setTimeZone(daily.getTimeZone());
            timeData.setTimeRange(daily.getTimeRange());
            timeData.setTmzonPdSe(daily.getTmzonPdSe());
            timeData.setTotalPopulation(daily.getTotalPopulation());
            timeData.setLocalPopulation(daily.getLocalPopulation());
            timeData.setTempForeignerPopulation(daily.getTempForeignerPopulation());
            timeData.setLongForeignerPopulation(daily.getLongForeignerPopulation());
            timeDataList.add(timeData);
        }
        
        timeBasedDto.setTimeDataList(timeDataList);
        return timeBasedDto;
    }
    
    // 동 이름으로 일별 생활인구 현황 조회
    public DailyPopulationDto getDailyPopulationByDongName(String dongName) {
        // 동 이름으로 행정동 코드 찾기
        String adstrdCode = findAdstrdCodeByDongName(dongName);
        if (adstrdCode == null) {
            return new DailyPopulationDto(); // 빈 데이터 반환
        }
        return getDailyPopulation(adstrdCode);
    }
    
    // 동 이름으로 행정동 코드 찾기
    private String findAdstrdCodeByDongName(String dongName) {
        List<GangnamPopulation> gangnamList = gangnamPopulationRepository.findAll();
        return gangnamList.stream()
                .filter(g -> g.getDongName() != null && g.getDongName().equals(dongName))
                .map(GangnamPopulation::getAdstrdCodeSe)
                .findFirst()
                .orElse(null);
    }
    
    // 일일 평균/최대/최소 생활인구 현황
    public DailyStatsDto getDailyStats(String adstrdCode) {
        DailyStatsDto dto = new DailyStatsDto();
        
        // 실제 데이터베이스에서 조회
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSe(adstrdCode);
        List<TempForeigner> tempForeignerList = tempForeignerRepository.findByAdstrdCodeSe(adstrdCode);
        List<LongForeigner> longForeignerList = longForeignerRepository.findByAdstrdCodeSe(adstrdCode);
        
        // 날짜별 총 인구 계산
        Map<String, Integer> dailyPopulation = new HashMap<>();
        
        // 국내인 데이터 처리
        for (LocalPeople local : localPeopleList) {
            String date = local.getStdrDeId();
            if (date != null) {
                int population = local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0;
                dailyPopulation.merge(date, population, Integer::sum);
            }
        }
        
        // 단기 외국인 데이터 처리
        for (TempForeigner temp : tempForeignerList) {
            String date = temp.getStdrDeId();
            if (date != null) {
                int population = temp.getTotLvpopCo() != null ? temp.getTotLvpopCo() : 0;
                dailyPopulation.merge(date, population, Integer::sum);
            }
        }
        
        // 장기 외국인 데이터 처리
        for (LongForeigner longForeigner : longForeignerList) {
            String date = longForeigner.getStdrDeId();
            if (date != null) {
                int population = longForeigner.getTotLvpopCo() != null ? longForeigner.getTotLvpopCo() : 0;
                dailyPopulation.merge(date, population, Integer::sum);
            }
        }
        
        if (dailyPopulation.isEmpty()) {
            dto.setAveragePopulation(0);
            dto.setMaxPopulation(0);
            dto.setMinPopulation(0);
            return dto;
        }
        
        // 통계 계산
        int totalPopulation = dailyPopulation.values().stream().mapToInt(Integer::intValue).sum();
        double averagePopulation = (double) totalPopulation / dailyPopulation.size();
        
        // 최대/최소 인구 찾기
        int maxPopulation = dailyPopulation.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        int minPopulation = dailyPopulation.values().stream().mapToInt(Integer::intValue).min().orElse(0);
        
        dto.setAveragePopulation((int) averagePopulation);
        dto.setMaxPopulation(maxPopulation);
        dto.setMinPopulation(minPopulation);
        
        return dto;
    }
    
    // 날짜 형식 변환 (YYYYMMDD -> YYYY-MM-DD)
    private String formatDate(String dateStr) {
        if (dateStr == null || dateStr.length() != 8) {
            return "2024-01-01";
        }
        return dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8);
    }
    
    // 시간대 코드를 사람이 읽기 쉬운 형태로 변환
    private void setTimeZoneInfo(DailyPopulationDto.DailyData data, String tmzonPdSe) {
        if (tmzonPdSe == null) {
            data.setTimeZone("알 수 없음");
            data.setTimeRange("알 수 없음");
            return;
        }
        
        try {
            int hour = Integer.parseInt(tmzonPdSe);
            int startHour;
            int endHour;
            String timeZone;

            if (hour == 0) {
                // 00 코드를 00:00-01:00, 새벽으로 처리
                startHour = 0;
                endHour = 1;
                timeZone = "새벽";
            } else if (hour >= 1 && hour <= 24) {
                startHour = hour - 1;
                endHour = hour;

                if (hour >= 1 && hour <= 5) {
                    timeZone = "새벽";
                } else if (hour >= 6 && hour <= 11) {
                    timeZone = "오전";
                } else if (hour >= 12 && hour <= 17) {
                    timeZone = "오후";
                } else if (hour >= 18 && hour <= 21) {
                    timeZone = "저녁";
                } else {
                    timeZone = "밤";
                }
            } else {
                data.setTimeZone("알 수 없음");
                data.setTimeRange("알 수 없음");
                return;
            }

            data.setTimeZone(timeZone);
            data.setTimeRange(String.format("%02d:00-%02d:00", startHour, endHour));
        } catch (NumberFormatException e) {
            data.setTimeZone("알 수 없음");
            data.setTimeRange("알 수 없음");
        }
    }
    
    // 주간/야간 생활인구 현황
    public DayNightPopulationDto getDayNightPopulation(String adstrdCode) {
        DayNightPopulationDto dto = new DayNightPopulationDto();
        
        // 실제 데이터베이스에서 조회
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSe(adstrdCode);
        
        int dayPopulation = 0;
        int nightPopulation = 0;
        
        for (LocalPeople local : localPeopleList) {
            String timeSlot = local.getTmzonPdSe();
            int population = local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0;
            
            // 시간대별로 주간/야간 구분 (실제 데이터에 따라 조정 필요)
            if (timeSlot.equals("00") || timeSlot.equals("01") || timeSlot.equals("02") || 
                timeSlot.equals("03") || timeSlot.equals("04") || timeSlot.equals("05")) {
                nightPopulation += population;
            } else {
                dayPopulation += population;
            }
        }
        
        double ratio = dayPopulation > 0 ? (double) dayPopulation / nightPopulation : 0.0;
        
        dto.setDayPopulation(dayPopulation);
        dto.setNightPopulation(nightPopulation);
        dto.setDayNightRatio(ratio);
        
        return dto;
    }
    
    // 시간대별/요일별 생활인구 현황
    public TimeWeekPopulationDto getTimeWeekPopulation(String adstrdCode) {
        TimeWeekPopulationDto dto = new TimeWeekPopulationDto();
        
        // 실제 데이터베이스에서 조회
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSe(adstrdCode);
        
        // 시간대별 인구 집계
        Map<String, Integer> timeSlotPopulation = new HashMap<>();
        Map<String, Integer> weekdayPopulation = new HashMap<>();
        
        for (LocalPeople local : localPeopleList) {
            String timeSlot = local.getTmzonPdSe();
            String date = local.getStdrDeId();
            
            // 시간대별 인구 집계
            timeSlotPopulation.merge(timeSlot, local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0, Integer::sum);
            
            // 요일별 인구 집계 (날짜에서 요일 추출)
            try {
                String weekday = getWeekdayFromDate(date);
                weekdayPopulation.merge(weekday, local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0, Integer::sum);
            } catch (Exception e) {
                // 날짜 파싱 실패 시 무시
            }
        }
        
        dto.setTimeSlotPopulation(timeSlotPopulation);
        dto.setWeekdayPopulation(weekdayPopulation);
        
        return dto;
    }
    
    // 날짜에서 요일 추출
    private String getWeekdayFromDate(String dateStr) {
        try {
            // YYYYMMDD 형식을 파싱
            int year = Integer.parseInt(dateStr.substring(0, 4));
            int month = Integer.parseInt(dateStr.substring(4, 6));
            int day = Integer.parseInt(dateStr.substring(6, 8));
            
            java.time.LocalDate date = java.time.LocalDate.of(year, month, day);
            java.time.DayOfWeek dayOfWeek = date.getDayOfWeek();
            
            switch (dayOfWeek) {
                case MONDAY: return "월요일";
                case TUESDAY: return "화요일";
                case WEDNESDAY: return "수요일";
                case THURSDAY: return "목요일";
                case FRIDAY: return "금요일";
                case SATURDAY: return "토요일";
                case SUNDAY: return "일요일";
                default: return "알 수 없음";
            }
        } catch (Exception e) {
            return "알 수 없음";
        }
    }
    
    // 주중/주말/연령대별 생활인구 현황
    public WeekdayWeekendPopulationDto getWeekdayWeekendPopulation(String adstrdCode) {
        WeekdayWeekendPopulationDto dto = new WeekdayWeekendPopulationDto();
        
        // 실제 데이터베이스에서 조회
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSe(adstrdCode);
        
        int weekdayPopulation = 0;
        int weekendPopulation = 0;
        
        for (LocalPeople local : localPeopleList) {
            String date = local.getStdrDeId();
            int population = local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0;
            
            try {
                if (isWeekend(date)) {
                    weekendPopulation += population;
                } else {
                    weekdayPopulation += population;
                }
            } catch (Exception e) {
                // 날짜 파싱 실패 시 주중으로 처리
                weekdayPopulation += population;
            }
        }
        
        dto.setWeekdayPopulation(weekdayPopulation);
        dto.setWeekendPopulation(weekendPopulation);
        
        return dto;
    }
    
    // 주말 여부 확인
    private boolean isWeekend(String dateStr) {
        try {
            String weekday = getWeekdayFromDate(dateStr);
            return weekday.equals("토요일") || weekday.equals("일요일");
        } catch (Exception e) {
            return false;
        }
    }
    
    // 연령대별 인구 추가
    private void addAgeGroupPopulation(LocalPeople local, Map<String, Integer> ageGroupMap) {
        // 20대
        int age20s = (local.getMaleF20t24LvpopCo() != null ? local.getMaleF20t24LvpopCo() : 0) +
                     (local.getMaleF25t29LvpopCo() != null ? local.getMaleF25t29LvpopCo() : 0) +
                     (local.getFemaleF20t24LvpopCo() != null ? local.getFemaleF20t24LvpopCo() : 0) +
                     (local.getFemaleF25t29LvpopCo() != null ? local.getFemaleF25t29LvpopCo() : 0);
        ageGroupMap.merge("20대", age20s, Integer::sum);
        
        // 30대
        int age30s = (local.getMaleF30t34LvpopCo() != null ? local.getMaleF30t34LvpopCo() : 0) +
                     (local.getMaleF35t39LvpopCo() != null ? local.getMaleF35t39LvpopCo() : 0) +
                     (local.getFemaleF30t34LvpopCo() != null ? local.getFemaleF30t34LvpopCo() : 0) +
                     (local.getFemaleF35t39LvpopCo() != null ? local.getFemaleF35t39LvpopCo() : 0);
        ageGroupMap.merge("30대", age30s, Integer::sum);
        
        // 40대
        int age40s = (local.getMaleF40t44LvpopCo() != null ? local.getMaleF40t44LvpopCo() : 0) +
                     (local.getMaleF45t49LvpopCo() != null ? local.getMaleF45t49LvpopCo() : 0) +
                     (local.getFemaleF40t44LvpopCo() != null ? local.getFemaleF40t44LvpopCo() : 0) +
                     (local.getFemaleF45t49LvpopCo() != null ? local.getFemaleF45t49LvpopCo() : 0);
        ageGroupMap.merge("40대", age40s, Integer::sum);
        
        // 50대
        int age50s = (local.getMaleF50t54LvpopCo() != null ? local.getMaleF50t54LvpopCo() : 0) +
                     (local.getMaleF55t59LvpopCo() != null ? local.getMaleF55t59LvpopCo() : 0) +
                     (local.getFemaleF50t54LvpopCo() != null ? local.getFemaleF50t54LvpopCo() : 0) +
                     (local.getFemaleF55t59LvpopCo() != null ? local.getFemaleF55t59LvpopCo() : 0);
        ageGroupMap.merge("50대", age50s, Integer::sum);
        
        // 60대+
        int age60s = (local.getMaleF60t64LvpopCo() != null ? local.getMaleF60t64LvpopCo() : 0) +
                     (local.getMaleF65t69LvpopCo() != null ? local.getMaleF65t69LvpopCo() : 0) +
                     (local.getMaleF70t74LvpopCo() != null ? local.getMaleF70t74LvpopCo() : 0) +
                     (local.getFemaleF60t64LvpopCo() != null ? local.getFemaleF60t64LvpopCo() : 0) +
                     (local.getFemaleF65t69LvpopCo() != null ? local.getFemaleF65t69LvpopCo() : 0) +
                     (local.getFemaleF70t74LvpopCo() != null ? local.getFemaleF70t74LvpopCo() : 0);
        ageGroupMap.merge("60대+", age60s, Integer::sum);
    }
    
    // 성별/연령별 생활인구 현황
    public GenderAgePopulationDto getGenderAgePopulation(String adstrdCode) {
        GenderAgePopulationDto dto = new GenderAgePopulationDto();
        
        // 실제 데이터베이스에서 조회
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSe(adstrdCode);
        
        // 성별/연령별 인구 집계
        Map<String, Integer> maleAgeGroups = new HashMap<>();
        Map<String, Integer> femaleAgeGroups = new HashMap<>();
        
        for (LocalPeople local : localPeopleList) {
            // 남성 연령대별 인구 집계
            maleAgeGroups.merge("0-9", local.getMaleF0t9LvpopCo() != null ? local.getMaleF0t9LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("10-14", local.getMaleF10t14LvpopCo() != null ? local.getMaleF10t14LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("15-19", local.getMaleF15t19LvpopCo() != null ? local.getMaleF15t19LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("20-24", local.getMaleF20t24LvpopCo() != null ? local.getMaleF20t24LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("25-29", local.getMaleF25t29LvpopCo() != null ? local.getMaleF25t29LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("30-34", local.getMaleF30t34LvpopCo() != null ? local.getMaleF30t34LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("35-39", local.getMaleF35t39LvpopCo() != null ? local.getMaleF35t39LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("40-44", local.getMaleF40t44LvpopCo() != null ? local.getMaleF40t44LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("45-49", local.getMaleF45t49LvpopCo() != null ? local.getMaleF45t49LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("50-54", local.getMaleF50t54LvpopCo() != null ? local.getMaleF50t54LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("55-59", local.getMaleF55t59LvpopCo() != null ? local.getMaleF55t59LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("60-64", local.getMaleF60t64LvpopCo() != null ? local.getMaleF60t64LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("65-69", local.getMaleF65t69LvpopCo() != null ? local.getMaleF65t69LvpopCo() : 0, Integer::sum);
            maleAgeGroups.merge("70+", local.getMaleF70t74LvpopCo() != null ? local.getMaleF70t74LvpopCo() : 0, Integer::sum);
            
            // 여성 연령대별 인구 집계
            femaleAgeGroups.merge("0-9", local.getFemaleF0t9LvpopCo() != null ? local.getFemaleF0t9LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("10-14", local.getFemaleF10t14LvpopCo() != null ? local.getFemaleF10t14LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("15-19", local.getFemaleF15t19LvpopCo() != null ? local.getFemaleF15t19LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("20-24", local.getFemaleF20t24LvpopCo() != null ? local.getFemaleF20t24LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("25-29", local.getFemaleF25t29LvpopCo() != null ? local.getFemaleF25t29LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("30-34", local.getFemaleF30t34LvpopCo() != null ? local.getFemaleF30t34LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("35-39", local.getFemaleF35t39LvpopCo() != null ? local.getFemaleF35t39LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("40-44", local.getFemaleF40t44LvpopCo() != null ? local.getFemaleF40t44LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("45-49", local.getFemaleF45t49LvpopCo() != null ? local.getFemaleF45t49LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("50-54", local.getFemaleF50t54LvpopCo() != null ? local.getFemaleF50t54LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("55-59", local.getFemaleF55t59LvpopCo() != null ? local.getFemaleF55t59LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("60-64", local.getFemaleF60t64LvpopCo() != null ? local.getFemaleF60t64LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("65-69", local.getFemaleF65t69LvpopCo() != null ? local.getFemaleF65t69LvpopCo() : 0, Integer::sum);
            femaleAgeGroups.merge("70+", local.getFemaleF70t74LvpopCo() != null ? local.getFemaleF70t74LvpopCo() : 0, Integer::sum);
        }
        
        dto.setMaleAgeGroup(maleAgeGroups);
        dto.setFemaleAgeGroup(femaleAgeGroups);
        
        return dto;
    }
    
    // Entity를 DTO로 변환
    private GangnamPopulationResponseDto convertToGangnamDto(GangnamPopulation gangnam) {
        GangnamPopulationResponseDto dto = new GangnamPopulationResponseDto();
        dto.setDongName(gangnam.getDongName());
        dto.setAdstrdCodeSe(gangnam.getAdstrdCodeSe());
        return dto;
    }
}
