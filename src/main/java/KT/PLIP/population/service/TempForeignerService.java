package KT.PLIP.population.service;

import KT.PLIP.population.domain.TempForeigner;
import KT.PLIP.population.dto.TempForeignerResponseDto;
import KT.PLIP.population.repository.TempForeignerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TempForeignerService {
    
    private final TempForeignerRepository tempForeignerRepository;
    
    // 특정 행정동의 단기체류 외국인 목록 조회
    public List<TempForeignerResponseDto> getTempForeignersByAdstrdCode(String adstrdCode) {
        List<TempForeigner> tempForeigners = tempForeignerRepository.findByAdstrdCodeSe(adstrdCode);
        return tempForeigners.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // 특정 행정동의 단기체류 외국인 상세 통계
    public TempForeignerResponseDto getTempForeignerStats(String adstrdCode) {
        List<TempForeigner> tempForeigners = tempForeignerRepository.findByAdstrdCodeSe(adstrdCode);
        
        if (tempForeigners.isEmpty()) {
            return new TempForeignerResponseDto();
        }
        
        // 첫 번째 데이터를 기준으로 통계 생성
        TempForeigner first = tempForeigners.get(0);
        TempForeignerResponseDto dto = convertToDto(first);
        
        // 추가 통계 계산 로직 (필요시 구현)
        
        return dto;
    }
    
    // 모든 단기체류 외국인 데이터 조회
    public List<TempForeignerResponseDto> getAllTempForeigners() {
        List<TempForeigner> tempForeigners = tempForeignerRepository.findAll();
        return tempForeigners.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Entity를 DTO로 변환
    private TempForeignerResponseDto convertToDto(TempForeigner tempForeigner) {
        TempForeignerResponseDto dto = new TempForeignerResponseDto();
        dto.setId(tempForeigner.getId());
        dto.setTotLvpopCo(tempForeigner.getTotLvpopCo());
        dto.setChinaStaypopCo(tempForeigner.getChinaStaypopCo());
        dto.setOtherStaypopCo(tempForeigner.getOtherStaypopCo());
        dto.setOaCd(tempForeigner.getOaCd());
        dto.setStdrDeId(tempForeigner.getStdrDeId());
        dto.setTmzonPdSe(tempForeigner.getTmzonPdSe());
        dto.setAdstrdCodeSe(tempForeigner.getAdstrdCodeSe());
        
        // 시간대 정보 설정
        setTimeZoneInfo(dto, tempForeigner.getTmzonPdSe());
        
        return dto;
    }
    
    // 시간대 코드를 사람이 읽기 쉬운 형태로 변환
    private void setTimeZoneInfo(TempForeignerResponseDto dto, String tmzonPdSe) {
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
