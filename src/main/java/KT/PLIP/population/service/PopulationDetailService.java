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
    
    // 강남구 동 목록 조회
    public List<GangnamPopulationResponseDto> getAllDongs() {
        List<GangnamPopulation> dongs = gangnamPopulationRepository.findAll();
        return dongs.stream()
                .map(this::convertToGangnamDto)
                .collect(Collectors.toList());
    }

    // 동 이름으로 상세 생활인구 현황 조회
    public PopulationDetailResponseDto getPopulationDetailByDongName(String dongName) {
        System.out.println("=== getPopulationDetailByDongName 시작 ===");
        System.out.println("요청된 동 이름: " + dongName);
        
        // 동 이름으로 행정동 코드 찾기
        GangnamPopulation gangnam = gangnamPopulationRepository.findByDongName(dongName)
                .orElseThrow(() -> new RuntimeException("동을 찾을 수 없습니다: " + dongName));
        
        System.out.println("찾은 동 정보: " + gangnam.getDongName() + ", 코드: " + gangnam.getAdstrdCodeSe());
        
        // 찾은 행정동 코드로 상세 정보 조회
        PopulationDetailResponseDto result = getPopulationDetail(gangnam.getAdstrdCodeSe());
        System.out.println("=== getPopulationDetailByDongName 완료 ===");
        return result;
    }
    
    // 특정 동의 상세 생활인구 현황 조회
    public PopulationDetailResponseDto getPopulationDetail(String adstrdCode) {
        System.out.println("=== getPopulationDetail 시작 ===");
        System.out.println("행정동 코드: " + adstrdCode);
        
        PopulationDetailResponseDto response = new PopulationDetailResponseDto();
        
        // 동 정보 조회
        Optional<GangnamPopulation> gangnamOpt = gangnamPopulationRepository.findByAdstrdCodeSe(adstrdCode);
        if (gangnamOpt.isPresent()) {
            GangnamPopulation gangnam = gangnamOpt.get();
            response.setDongName(gangnam.getDongName());
            response.setAdstrdCodeSe(gangnam.getAdstrdCodeSe());
            System.out.println("동 정보 설정 완료: " + gangnam.getDongName());
        } else {
            System.out.println("동 정보를 찾을 수 없음: " + adstrdCode);
        }
        
        try {
            System.out.println("일별 생활인구 현황 조회 시작...");
            response.setDailyPopulation(getDailyPopulation(adstrdCode));
            System.out.println("일별 생활인구 현황 조회 완료");
            
            System.out.println("일일 통계 조회 시작...");
            response.setDailyStats(getDailyStats(adstrdCode));
            System.out.println("일일 통계 조회 완료");
            
            System.out.println("주간/야간 생활인구 현황 조회 시작...");
            response.setDayNightPopulation(getDayNightPopulation(adstrdCode));
            System.out.println("주간/야간 생활인구 현황 조회 완료");
            
            System.out.println("시간대별/요일별 생활인구 현황 조회 시작...");
            response.setTimeWeekPopulation(getTimeWeekPopulation(adstrdCode));
            System.out.println("시간대별/요일별 생활인구 현황 조회 완료");
            
            System.out.println("주중/주말/연령대별 생활인구 현황 조회 시작...");
            response.setWeekdayWeekendPopulation(getWeekdayWeekendPopulation(adstrdCode));
            System.out.println("주중/주말/연령대별 생활인구 현황 조회 완료");
            
            System.out.println("성별/연령별 생활인구 현황 조회 시작...");
            response.setGenderAgePopulation(getGenderAgePopulation(adstrdCode));
            System.out.println("성별/연령별 생활인구 현황 조회 완료");
            
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        
        System.out.println("=== getPopulationDetail 완료 ===");
        return response;
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
        
        // 날짜+시간대별로 그룹화된 데이터 저장
        Map<String, DailyPopulationDto.DailyData> groupedData = new HashMap<>();
        
        // 1. LocalPeople 데이터 그룹화 및 합산
        for (LocalPeople local : localPeopleList) {
            String key = local.getStdrDeId() + "_" + local.getTmzonPdSe();
            
            DailyPopulationDto.DailyData data = groupedData.computeIfAbsent(key, k -> {
                DailyPopulationDto.DailyData newData = new DailyPopulationDto.DailyData();
                newData.setDate(local.getStdrDeId() != null ? local.getStdrDeId() : "20240101");
                newData.setTmzonPdSe(local.getTmzonPdSe());
                newData.setTimeZone(setTimeZoneInfo(local.getTmzonPdSe()));
                newData.setTimeRange(setTimeRangeInfo(local.getTmzonPdSe()));
                newData.setLocalPopulation(0.0);
                newData.setTempForeignerPopulation(0.0);
                newData.setLongForeignerPopulation(0.0);
                return newData;
            });
            
            // 기존 데이터에 합산
            data.setLocalPopulation(data.getLocalPopulation() + (local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0.0));
        }
        
        // 2. TempForeigner 데이터 그룹화 및 합산
        for (TempForeigner temp : tempForeignerList) {
            String key = temp.getStdrDeId() + "_" + temp.getTmzonPdSe();
            
            DailyPopulationDto.DailyData data = groupedData.computeIfAbsent(key, k -> {
                DailyPopulationDto.DailyData newData = new DailyPopulationDto.DailyData();
                newData.setDate(temp.getStdrDeId() != null ? temp.getStdrDeId() : "20240101");
                newData.setTmzonPdSe(temp.getTmzonPdSe());
                newData.setTimeZone(setTimeZoneInfo(temp.getTmzonPdSe()));
                newData.setTimeRange(setTimeRangeInfo(temp.getTmzonPdSe()));
                newData.setLocalPopulation(0.0);
                newData.setTempForeignerPopulation(0.0);
                newData.setLongForeignerPopulation(0.0);
                return newData;
            });
            
            // 기존 데이터에 합산
            data.setTempForeignerPopulation(data.getTempForeignerPopulation() + (temp.getTotLvpopCo() != null ? temp.getTotLvpopCo() : 0.0));
        }
        
        // 3. LongForeigner 데이터 그룹화 및 합산
        for (LongForeigner longForeigner : longForeignerList) {
            String key = longForeigner.getStdrDeId() + "_" + longForeigner.getTmzonPdSe();
            
            DailyPopulationDto.DailyData data = groupedData.computeIfAbsent(key, k -> {
                DailyPopulationDto.DailyData newData = new DailyPopulationDto.DailyData();
                newData.setDate(longForeigner.getStdrDeId() != null ? longForeigner.getStdrDeId() : "20240101");
                newData.setTmzonPdSe(longForeigner.getTmzonPdSe());
                newData.setTimeZone(setTimeZoneInfo(longForeigner.getTmzonPdSe()));
                newData.setTimeRange(setTimeRangeInfo(longForeigner.getTmzonPdSe()));
                newData.setLocalPopulation(0.0);
                newData.setTempForeignerPopulation(0.0);
                newData.setLongForeignerPopulation(0.0);
                return newData;
            });
            
            // 기존 데이터에 합산
            data.setLongForeignerPopulation(data.getLongForeignerPopulation() + (longForeigner.getTotLvpopCo() != null ? longForeigner.getTotLvpopCo() : 0.0));
        }
        
        // 4. 총 인구 계산 및 리스트에 추가
        for (DailyPopulationDto.DailyData data : groupedData.values()) {
            data.setTotalPopulation(
                data.getLocalPopulation() + 
                data.getTempForeignerPopulation() + 
                data.getLongForeignerPopulation()
            );
            
            System.out.println("그룹화된 데이터 - 날짜: " + data.getDate() + 
                              ", 시간대: " + data.getTmzonPdSe() + 
                              ", 총인구: " + data.getTotalPopulation());
            
            dailyDataList.add(data);
        }
        
        // 5. 날짜와 시간대 순으로 정렬
        dailyDataList.sort((a, b) -> {
            int dateCompare = a.getDate().compareTo(b.getDate());
            if (dateCompare != 0) return dateCompare;
            return a.getTmzonPdSe().compareTo(b.getTmzonPdSe());
        });
        
        System.out.println("getDailyPopulation 완료 - 생성된 일별 데이터 개수: " + dailyDataList.size());
        dto.setDailyDataList(dailyDataList);
        return dto;
    }

    
    // 일일 평균/최대/최소 생활인구 현황
    public DailyStatsDto getDailyStats(String adstrdCode) {
        DailyStatsDto dto = new DailyStatsDto();
        
        // 실제 데이터베이스에서 조회
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSe(adstrdCode);
        List<TempForeigner> tempForeignerList = tempForeignerRepository.findByAdstrdCodeSe(adstrdCode);
        List<LongForeigner> longForeignerList = longForeignerRepository.findByAdstrdCodeSe(adstrdCode);
        
        // 날짜별 총 인구 계산
        Map<String, Double> dailyPopulation = new HashMap<>();
        
        // 국내인 데이터 처리
        for (LocalPeople local : localPeopleList) {
            String date = local.getStdrDeId();
            if (date != null) {
                double population = local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0.0;
                dailyPopulation.merge(date, population, Double::sum);
            }
        }
        
        // 단기 외국인 데이터 처리
        for (TempForeigner temp : tempForeignerList) {
            String date = temp.getStdrDeId();
            if (date != null) {
                double population = temp.getTotLvpopCo() != null ? temp.getTotLvpopCo() : 0.0;
                dailyPopulation.merge(date, population, Double::sum);
            }
        }
        
        // 장기 외국인 데이터 처리
        for (LongForeigner longForeigner : longForeignerList) {
            String date = longForeigner.getStdrDeId();
            if (date != null) {
                double population = longForeigner.getTotLvpopCo() != null ? longForeigner.getTotLvpopCo() : 0.0;
                dailyPopulation.merge(date, population, Double::sum);
            }
        }
        
        if (dailyPopulation.isEmpty()) {
            dto.setAveragePopulation(0.0);
            dto.setMaxPopulation(0.0);
            dto.setMinPopulation(0.0);
            return dto;
        }
        
        // 통계 계산
        double totalPopulation = dailyPopulation.values().stream().mapToDouble(Double::doubleValue).sum();
        double averagePopulation = totalPopulation / dailyPopulation.size();
        
        // 최대/최소 날짜와 인구 찾기
        double maxPopulation = Math.round(dailyPopulation.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0));
        double minPopulation = Math.round(dailyPopulation.values().stream().mapToDouble(Double::doubleValue).min().orElse(0.0));
        
        dto.setAveragePopulation(averagePopulation);
        dto.setMaxPopulation(maxPopulation);
        dto.setMinPopulation(minPopulation);
        
        return dto;
    }
    
    // 날짜 형식 변환 (YYYYMMDD -> YYYY-MM-DD)
    private String formatDate(String dateStr) {
        if (dateStr == null || dateStr.length() != 8) {
            return "2024-01-01";
        }
        try {
            return dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8);
        } catch (Exception e) {
            return "2024-01-01";
        }
    }
    
    // 주간/야간 생활인구 현황
    public DayNightPopulationDto getDayNightPopulation(String adstrdCode) {
        DayNightPopulationDto dto = new DayNightPopulationDto();
        
        // 실제 데이터베이스에서 조회
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSe(adstrdCode);
        
        double dayPopulation = 0.0;
        double nightPopulation = 0.0;
        
        for (LocalPeople local : localPeopleList) {
            String timeSlot = local.getTmzonPdSe();
            double population = local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0.0;
            
            // 시간대별로 주간/야간 구분 (실제 데이터에 따라 조정 필요)
            if (timeSlot.equals("00") || timeSlot.equals("01") || timeSlot.equals("02") || 
                timeSlot.equals("03") || timeSlot.equals("04") || timeSlot.equals("05")) {
                nightPopulation += population;
            } else {
                dayPopulation += population;
            }
        }
        
        double ratio = nightPopulation > 0 ? dayPopulation / nightPopulation : 0.0;
        
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
        Map<String, Double> timeSlotPopulation = new HashMap<>();
        Map<String, Double> weekdayPopulation = new HashMap<>();
        
        for (LocalPeople local : localPeopleList) {
            String timeSlot = local.getTmzonPdSe();
            String date = local.getStdrDeId();
            
            // 시간대별 인구 집계
            timeSlotPopulation.merge(timeSlot, local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0.0, Double::sum);
            
            // 요일별 인구 집계 (날짜에서 요일 추출)
            try {
                String weekday = getWeekdayFromDate(date);
                weekdayPopulation.merge(weekday, local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0.0, Double::sum);
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
        
        double weekdayTotal = 0.0;
        double weekendTotal = 0.0;
        Map<String, Double> ageGroupWeekday = new HashMap<>();
        Map<String, Double> ageGroupWeekend = new HashMap<>();
        
        for (LocalPeople local : localPeopleList) {
            String date = local.getStdrDeId();
            boolean isWeekend = isWeekend(date);
            
            double totalPopulation = local.getTotLvpopCo() != null ? local.getTotLvpopCo() : 0.0;
            
            if (isWeekend) {
                weekendTotal += totalPopulation;
                addAgeGroupPopulation(local, ageGroupWeekend);
            } else {
                weekdayTotal += totalPopulation;
                addAgeGroupPopulation(local, ageGroupWeekday);
            }
        }
        
        dto.setWeekdayPopulation(weekdayTotal);
        dto.setWeekendPopulation(weekendTotal);
        dto.setAgeGroupWeekday(ageGroupWeekday);
        dto.setAgeGroupWeekend(ageGroupWeekend);
        
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
    private void addAgeGroupPopulation(LocalPeople local, Map<String, Double> ageGroupMap) {
        // 20대
        double age20s = (local.getMaleF20t24LvpopCo() != null ? local.getMaleF20t24LvpopCo() : 0.0) +
                        (local.getMaleF25t29LvpopCo() != null ? local.getMaleF25t29LvpopCo() : 0.0) +
                        (local.getFemaleF20t24LvpopCo() != null ? local.getFemaleF20t24LvpopCo() : 0.0) +
                        (local.getFemaleF25t29LvpopCo() != null ? local.getFemaleF25t29LvpopCo() : 0.0);
        ageGroupMap.merge("20대", age20s, Double::sum);
        
        // 30대
        double age30s = (local.getMaleF30t34LvpopCo() != null ? local.getMaleF30t34LvpopCo() : 0.0) +
                        (local.getMaleF35t39LvpopCo() != null ? local.getMaleF35t39LvpopCo() : 0.0) +
                        (local.getFemaleF30t34LvpopCo() != null ? local.getFemaleF30t34LvpopCo() : 0.0) +
                        (local.getFemaleF35t39LvpopCo() != null ? local.getFemaleF35t39LvpopCo() : 0.0);
        ageGroupMap.merge("30대", age30s, Double::sum);
        
        // 40대
        double age40s = (local.getMaleF40t44LvpopCo() != null ? local.getMaleF40t44LvpopCo() : 0.0) +
                        (local.getMaleF45t49LvpopCo() != null ? local.getMaleF45t49LvpopCo() : 0.0) +
                        (local.getFemaleF40t44LvpopCo() != null ? local.getFemaleF40t44LvpopCo() : 0.0) +
                        (local.getFemaleF45t49LvpopCo() != null ? local.getFemaleF45t49LvpopCo() : 0.0);
        ageGroupMap.merge("40대", age40s, Double::sum);
        
        // 50대
        double age50s = (local.getMaleF50t54LvpopCo() != null ? local.getMaleF50t54LvpopCo() : 0.0) +
                        (local.getMaleF55t59LvpopCo() != null ? local.getMaleF55t59LvpopCo() : 0.0) +
                        (local.getFemaleF50t54LvpopCo() != null ? local.getFemaleF50t54LvpopCo() : 0.0) +
                        (local.getFemaleF55t59LvpopCo() != null ? local.getFemaleF55t59LvpopCo() : 0.0);
        ageGroupMap.merge("50대", age50s, Double::sum);
        
        // 60대+
        double age60s = (local.getMaleF60t64LvpopCo() != null ? local.getMaleF60t64LvpopCo() : 0.0) +
                        (local.getMaleF65t69LvpopCo() != null ? local.getMaleF65t69LvpopCo() : 0.0) +
                        (local.getMaleF70t74LvpopCo() != null ? local.getMaleF70t74LvpopCo() : 0.0) +
                        (local.getFemaleF60t64LvpopCo() != null ? local.getFemaleF60t64LvpopCo() : 0.0) +
                        (local.getFemaleF65t69LvpopCo() != null ? local.getFemaleF65t69LvpopCo() : 0.0) +
                        (local.getFemaleF70t74LvpopCo() != null ? local.getFemaleF70t74LvpopCo() : 0.0);
        ageGroupMap.merge("60대+", age60s, Double::sum);
    }
    
    // 성별/연령별 생활인구 현황
    public GenderAgePopulationDto getGenderAgePopulation(String adstrdCode) {
        GenderAgePopulationDto dto = new GenderAgePopulationDto();
        
        // 실제 데이터베이스에서 조회
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSe(adstrdCode);
        
        // 전체 레코드를 누적하여 세부 연령대 집계 (0-9, 10-14 ... 70+)
        Map<String, Double> maleAgeGroup = new LinkedHashMap<>();
        Map<String, Double> femaleAgeGroup = new LinkedHashMap<>();
        String[] buckets = {"0-9","10-14","15-19","20-24","25-29","30-34","35-39","40-44","45-49","50-54","55-59","60-64","65-69","70+"};
        for (String b : buckets) { maleAgeGroup.put(b, 0.0); femaleAgeGroup.put(b, 0.0); }

        for (LocalPeople lp : localPeopleList) {
            maleAgeGroup.merge("0-9",  Optional.ofNullable(lp.getMaleF0t9LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("10-14", Optional.ofNullable(lp.getMaleF10t14LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("15-19", Optional.ofNullable(lp.getMaleF15t19LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("20-24", Optional.ofNullable(lp.getMaleF20t24LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("25-29", Optional.ofNullable(lp.getMaleF25t29LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("30-34", Optional.ofNullable(lp.getMaleF30t34LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("35-39", Optional.ofNullable(lp.getMaleF35t39LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("40-44", Optional.ofNullable(lp.getMaleF40t44LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("45-49", Optional.ofNullable(lp.getMaleF45t49LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("50-54", Optional.ofNullable(lp.getMaleF50t54LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("55-59", Optional.ofNullable(lp.getMaleF55t59LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("60-64", Optional.ofNullable(lp.getMaleF60t64LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("65-69", Optional.ofNullable(lp.getMaleF65t69LvpopCo()).orElse(0.0), Double::sum);
            maleAgeGroup.merge("70+",  Optional.ofNullable(lp.getMaleF70t74LvpopCo()).orElse(0.0), Double::sum);

            femaleAgeGroup.merge("0-9",  Optional.ofNullable(lp.getFemaleF0t9LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("10-14", Optional.ofNullable(lp.getFemaleF10t14LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("15-19", Optional.ofNullable(lp.getFemaleF15t19LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("20-24", Optional.ofNullable(lp.getFemaleF20t24LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("25-29", Optional.ofNullable(lp.getFemaleF25t29LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("30-34", Optional.ofNullable(lp.getFemaleF30t34LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("35-39", Optional.ofNullable(lp.getFemaleF35t39LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("40-44", Optional.ofNullable(lp.getFemaleF40t44LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("45-49", Optional.ofNullable(lp.getFemaleF45t49LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("50-54", Optional.ofNullable(lp.getFemaleF50t54LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("55-59", Optional.ofNullable(lp.getFemaleF55t59LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("60-64", Optional.ofNullable(lp.getFemaleF60t64LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("65-69", Optional.ofNullable(lp.getFemaleF65t69LvpopCo()).orElse(0.0), Double::sum);
            femaleAgeGroup.merge("70+",  Optional.ofNullable(lp.getFemaleF70t74LvpopCo()).orElse(0.0), Double::sum);
        }

        dto.setMaleAgeGroup(maleAgeGroup);
        dto.setFemaleAgeGroup(femaleAgeGroup);
        dto.setMalePopulation(maleAgeGroup.values().stream().mapToDouble(Double::doubleValue).sum());
        dto.setFemalePopulation(femaleAgeGroup.values().stream().mapToDouble(Double::doubleValue).sum());

        return dto;
    }
    
    // Entity를 DTO로 변환
    private GangnamPopulationResponseDto convertToGangnamDto(GangnamPopulation gangnam) {
        GangnamPopulationResponseDto dto = new GangnamPopulationResponseDto();
        dto.setDongName(gangnam.getDongName());
        dto.setAdstrdCodeSe(gangnam.getAdstrdCodeSe());
        return dto;
    }
    
    // 시간대별 생활인구 현황 (일별 데이터에서 변환)
    public TimeBasedPopulationDto getTimeBasedPopulation(String adstrdCode) {
        DailyPopulationDto daily = getDailyPopulation(adstrdCode);
        TimeBasedPopulationDto result = new TimeBasedPopulationDto();
        List<TimeBasedPopulationDto.TimeData> list = new ArrayList<>();

        for (DailyPopulationDto.DailyData d : daily.getDailyDataList()) {
            TimeBasedPopulationDto.TimeData t = new TimeBasedPopulationDto.TimeData();
            t.setDate(d.getDate());
            t.setTimeZone(d.getTimeZone());
            t.setTimeRange(d.getTimeRange());
            t.setTmzonPdSe(d.getTmzonPdSe());
            t.setTotalPopulation(d.getTotalPopulation());
            t.setLocalPopulation(d.getLocalPopulation());
            t.setTempForeignerPopulation(d.getTempForeignerPopulation());
            t.setLongForeignerPopulation(d.getLongForeignerPopulation());
            list.add(t);
        }

        result.setTimeDataList(list);
        return result;
    }
    
    // 성별별 총 인구수
    public GenderPopulationDto getGenderPopulation(String adstrdCode) {
        GenderAgePopulationDto genderAge = getGenderAgePopulation(adstrdCode);
        GenderPopulationDto dto = new GenderPopulationDto();
        
        dto.setMaleTotal(genderAge.getMalePopulation());
        dto.setFemaleTotal(genderAge.getFemalePopulation());
        dto.setTotalPopulation(dto.getMaleTotal() + dto.getFemaleTotal());
        
        if (dto.getTotalPopulation() > 0) {
            dto.setMaleRatio((dto.getMaleTotal() / dto.getTotalPopulation()) * 100);
            dto.setFemaleRatio((dto.getFemaleTotal() / dto.getTotalPopulation()) * 100);
        } else {
            dto.setMaleRatio(0.0);
            dto.setFemaleRatio(0.0);
        }
        
        return dto;
    }
    
    // 성별별 시간대 분포
    public GenderTimeDistributionDto getGenderTimeDistribution(String adstrdCode) {
        GenderTimeDistributionDto dto = new GenderTimeDistributionDto();
        Map<String, Double> maleTimeDistribution = new HashMap<>();
        Map<String, Double> femaleTimeDistribution = new HashMap<>();
        
        // 실제 데이터베이스에서 조회
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSe(adstrdCode);
        
        for (LocalPeople local : localPeopleList) {
            String timeZone = setTimeZoneInfo(local.getTmzonPdSe());
            double malePopulation = calculateMalePopulation(local);
            double femalePopulation = calculateFemalePopulation(local);
            
            maleTimeDistribution.merge(timeZone, malePopulation, Double::sum);
            femaleTimeDistribution.merge(timeZone, femalePopulation, Double::sum);
        }
        
        dto.setMale(maleTimeDistribution);
        dto.setFemale(femaleTimeDistribution);
        
        return dto;
    }
    
    // 시간대 정보 설정
    private String setTimeZoneInfo(String tmzonPdSe) {
        if (tmzonPdSe == null) return "알 수 없음";
        
        switch (tmzonPdSe) {
            case "00": return "새벽";
            case "01": return "새벽";
            case "02": return "새벽";
            case "03": return "새벽";
            case "04": return "새벽";
            case "05": return "새벽";
            case "06": return "오전";
            case "07": return "오전";
            case "08": return "오전";
            case "09": return "오전";
            case "10": return "오전";
            case "11": return "오전";
            case "12": return "오후";
            case "13": return "오후";
            case "14": return "오후";
            case "15": return "오후";
            case "16": return "오후";
            case "17": return "오후";
            case "18": return "저녁";
            case "19": return "저녁";
            case "20": return "저녁";
            case "21": return "저녁";
            case "22": return "밤";
            case "23": return "밤";
            default: return "알 수 없음";
        }
    }
    
    // 시간 범위 정보 설정
    private String setTimeRangeInfo(String tmzonPdSe) {
        if (tmzonPdSe == null) return "알 수 없음";
        
        switch (tmzonPdSe) {
            case "00": return "00:00-01:00";
            case "01": return "01:00-02:00";
            case "02": return "02:00-03:00";
            case "03": return "03:00-04:00";
            case "04": return "04:00-05:00";
            case "05": return "05:00-06:00";
            case "06": return "06:00-07:00";
            case "07": return "07:00-08:00";
            case "08": return "08:00-09:00";
            case "09": return "09:00-10:00";
            case "10": return "10:00-11:00";
            case "11": return "11:00-12:00";
            case "12": return "12:00-13:00";
            case "13": return "13:00-14:00";
            case "14": return "14:00-15:00";
            case "15": return "15:00-16:00";
            case "16": return "16:00-17:00";
            case "17": return "17:00-18:00";
            case "18": return "18:00-19:00";
            case "19": return "19:00-20:00";
            case "20": return "20:00-21:00";
            case "21": return "21:00-22:00";
            case "22": return "22:00-23:00";
            case "23": return "23:00-24:00";
            default: return "알 수 없음";
        }
    }
    
    // 남성 인구 계산
    private double calculateMalePopulation(LocalPeople local) {
        return Optional.ofNullable(local.getMaleF0t9LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF10t14LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF15t19LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF20t24LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF25t29LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF30t34LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF35t39LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF40t44LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF45t49LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF50t54LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF55t59LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF60t64LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF65t69LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getMaleF70t74LvpopCo()).orElse(0.0);
    }
    
    // 여성 인구 계산
    private double calculateFemalePopulation(LocalPeople local) {
        return Optional.ofNullable(local.getFemaleF0t9LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF10t14LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF15t19LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF20t24LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF25t29LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF30t34LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF35t39LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF40t44LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF45t49LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF50t54LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF55t59LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF60t64LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF65t69LvpopCo()).orElse(0.0) +
               Optional.ofNullable(local.getFemaleF70t74LvpopCo()).orElse(0.0);
    }
    
    // 한달 전 대비 인구 변화 조회
    public PopulationChangeDto getPopulationChange(String adstrdCode) {
        return getPopulationChange(adstrdCode, null);
    }
    
    // 특정 날짜 기준으로 한달 전 대비 인구 변화 조회
    public PopulationChangeDto getPopulationChange(String adstrdCode, String currentDate) {
        PopulationChangeDto dto = new PopulationChangeDto();
        dto.setAdstrdCodeSe(adstrdCode);
        
        // 동 정보 조회
        Optional<GangnamPopulation> gangnamOpt = gangnamPopulationRepository.findByAdstrdCodeSe(adstrdCode);
        if (gangnamOpt.isPresent()) {
            dto.setDongName(gangnamOpt.get().getDongName());
        }
        
        // 현재 날짜 설정 (기본값: 오늘)
        if (currentDate == null) {
            currentDate = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        dto.setCurrentDate(currentDate);
        
        // 한달 전 날짜 계산
        String previousDate = calculatePreviousMonthDate(currentDate);
        dto.setPreviousDate(previousDate);
        
        // 현재 인구 조회
        Map<String, Double> currentPopulation = getPopulationByDate(adstrdCode, currentDate);
        dto.setCurrentTotalPopulation(currentPopulation.get("total"));
        
        // 이전 인구 조회
        Map<String, Double> previousPopulation = getPopulationByDate(adstrdCode, previousDate);
        dto.setPreviousTotalPopulation(previousPopulation.get("total"));
        
        // 증감 수 계산
        dto.setTotalPopulationChange(
            Optional.ofNullable(dto.getCurrentTotalPopulation()).orElse(0.0) - 
            Optional.ofNullable(dto.getPreviousTotalPopulation()).orElse(0.0)
        );
        
        // 증감률 계산 (%)
        dto.setTotalPopulationChangeRate(calculateChangeRate(
            dto.getPreviousTotalPopulation(), dto.getCurrentTotalPopulation()));
        
        return dto;
    }
    
    // 특정 날짜의 인구 조회
    private Map<String, Double> getPopulationByDate(String adstrdCode, String date) {
        Map<String, Double> result = new HashMap<>();
        result.put("total", 0.0);
        
        // LocalPeople 데이터 조회
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSeAndStdrDeId(adstrdCode, date);
        Double localTotal = localPeopleList.stream()
                .mapToDouble(local -> Optional.ofNullable(local.getTotLvpopCo()).orElse(0.0))
                .sum();
        result.put("local", localTotal);
        
        // TempForeigner 데이터 조회
        List<TempForeigner> tempForeignerList = tempForeignerRepository.findByAdstrdCodeSeAndStdrDeId(adstrdCode, date);
        Double tempTotal = tempForeignerList.stream()
                .mapToDouble(temp -> Optional.ofNullable(temp.getTotLvpopCo()).orElse(0.0))
                .sum();
        result.put("temp", tempTotal);
        
        // LongForeigner 데이터 조회
        List<LongForeigner> longForeignerList = longForeignerRepository.findByAdstrdCodeSeAndStdrDeId(adstrdCode, date);
        Double longTotal = longForeignerList.stream()
                .mapToDouble(longF -> Optional.ofNullable(longF.getTotLvpopCo()).orElse(0.0))
                .sum();
        result.put("long", longTotal);
        
        // 총 인구 계산
        result.put("total", localTotal + tempTotal + longTotal);
        
        return result;
    }
    
    // 한달 전 날짜 계산 (YYYYMMDD 형식)
    private String calculatePreviousMonthDate(String currentDate) {
        try {
            int year = Integer.parseInt(currentDate.substring(0, 4));
            int month = Integer.parseInt(currentDate.substring(4, 6));
            int day = Integer.parseInt(currentDate.substring(6, 8));
            
            java.time.LocalDate date = java.time.LocalDate.of(year, month, day);
            java.time.LocalDate previousMonth = date.minusMonths(1);
            
            return previousMonth.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (Exception e) {
            // 오류 발생 시 기본값 반환
            return "20240101";
        }
    }
    
    // 증감률 계산 (%)
    private Double calculateChangeRate(Double previous, Double current) {
        if (previous == null || previous == 0.0) {
            return current != null && current > 0.0 ? 100.0 : 0.0;
        }
        
        if (current == null) {
            return -100.0;
        }
        
        return ((current - previous) / previous) * 100.0;
    }
}
