package KT.PLIP.population.service;

import KT.PLIP.population.domain.LongForeigner;
import KT.PLIP.population.dto.LongForeignerResponseDto;
import KT.PLIP.population.repository.LongForeignerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LongForeignerService {
    
    private final LongForeignerRepository longForeignerRepository;
    
    // 특정 행정동의 장기체류 외국인 목록 조회
    public List<LongForeignerResponseDto> getLongForeignersByAdstrdCode(String adstrdCode) {
        List<LongForeigner> longForeigners = longForeignerRepository.findByAdstrdCodeSe(adstrdCode);
        return longForeigners.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // 특정 행정동의 장기체류 외국인 상세 통계
    public LongForeignerResponseDto getLongForeignerStats(String adstrdCode) {
        List<LongForeigner> longForeigners = longForeignerRepository.findByAdstrdCodeSe(adstrdCode);
        
        if (longForeigners.isEmpty()) {
            return new LongForeignerResponseDto();
        }
        
        // 첫 번째 데이터를 기준으로 통계 생성
        LongForeigner first = longForeigners.get(0);
        LongForeignerResponseDto dto = convertToDto(first);
        
        // 추가 통계 계산 로직 (필요시 구현)
        
        return dto;
    }
    
    // 모든 장기체류 외국인 데이터 조회
    public List<LongForeignerResponseDto> getAllLongForeigners() {
        List<LongForeigner> longForeigners = longForeignerRepository.findAll();
        return longForeigners.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Entity를 DTO로 변환
    private LongForeignerResponseDto convertToDto(LongForeigner longForeigner) {
        LongForeignerResponseDto dto = new LongForeignerResponseDto();
        dto.setId(longForeigner.getId());
        dto.setTotLvpopCo(longForeigner.getTotLvpopCo());
        dto.setChinaStaypopCo(longForeigner.getChinaStaypopCo());
        dto.setOtherStaypopCo(longForeigner.getOtherStaypopCo());
        dto.setOaCd(longForeigner.getOaCd());
        dto.setStdrDeId(longForeigner.getStdrDeId());
        dto.setTmzonPdSe(longForeigner.getTmzonPdSe());
        dto.setAdstrdCodeSe(longForeigner.getAdstrdCodeSe());
        
        // 시간대 정보 설정
        setTimeZoneInfo(dto, longForeigner.getTmzonPdSe());
        
        return dto;
    }
    
    // 시간대 코드를 사람이 읽기 쉬운 형태로 변환
    private void setTimeZoneInfo(LongForeignerResponseDto dto, String tmzonPdSe) {
        if (tmzonPdSe == null) {
            dto.setTimeZone("알 수 없음");
            dto.setTimeRange("알 수 없음");
            return;
        }
        
        try {
            int hour = Integer.parseInt(tmzonPdSe);
            if (hour >= 1 && hour <= 24) {
                int startHour = hour - 1;
                int endHour = hour;
                
                String timeZone;
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
                
                dto.setTimeZone(timeZone);
                dto.setTimeRange(String.format("%02d:00-%02d:00", startHour, endHour));
            } else {
                dto.setTimeZone("알 수 없음");
                dto.setTimeRange("알 수 없음");
            }
        } catch (NumberFormatException e) {
            dto.setTimeZone("알 수 없음");
            dto.setTimeRange("알 수 없음");
        }
    }
}
