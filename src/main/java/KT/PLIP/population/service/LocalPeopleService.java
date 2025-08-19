package KT.PLIP.population.service;

import KT.PLIP.population.domain.LocalPeople;
import KT.PLIP.population.dto.LocalPeopleResponseDto;
import KT.PLIP.population.repository.LocalPeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocalPeopleService {
    
    private final LocalPeopleRepository localPeopleRepository;
    
    // 모든 인구 데이터 조회
    public List<LocalPeopleResponseDto> getAllLocalPeople() {
        List<LocalPeople> localPeopleList = localPeopleRepository.findAll();
        return localPeopleList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // 행정동 코드로 조회
    public List<LocalPeopleResponseDto> getLocalPeopleByAdstrdCode(String adstrdCode) {
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodeSe(adstrdCode);
        return localPeopleList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // 특정 인구 수 이상인 지역 조회
    public List<LocalPeopleResponseDto> getLocalPeopleByMinPopulation(Integer minPopulation) {
        List<LocalPeople> localPeopleList = localPeopleRepository.findByTotLvpopCoGreaterThan(minPopulation);
        return localPeopleList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // 여러 행정동 코드로 조회
    public List<LocalPeopleResponseDto> getLocalPeopleByAdstrdCodes(List<String> adstrdCodes) {
        List<LocalPeople> localPeopleList = localPeopleRepository.findByAdstrdCodes(adstrdCodes);
        return localPeopleList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Entity를 DTO로 변환
    private LocalPeopleResponseDto convertToDto(LocalPeople localPeople) {
        LocalPeopleResponseDto dto = new LocalPeopleResponseDto();
        dto.setId(localPeople.getId());
        dto.setTotLvpopCo(localPeople.getTotLvpopCo());
        dto.setMaleF0t9LvpopCo(localPeople.getMaleF0t9LvpopCo());
        dto.setMaleF10t14LvpopCo(localPeople.getMaleF10t14LvpopCo());
        dto.setMaleF15t19LvpopCo(localPeople.getMaleF15t19LvpopCo());
        dto.setMaleF20t24LvpopCo(localPeople.getMaleF20t24LvpopCo());
        dto.setMaleF25t29LvpopCo(localPeople.getMaleF25t29LvpopCo());
        dto.setMaleF30t34LvpopCo(localPeople.getMaleF30t34LvpopCo());
        dto.setMaleF35t39LvpopCo(localPeople.getMaleF35t39LvpopCo());
        dto.setMaleF40t44LvpopCo(localPeople.getMaleF40t44LvpopCo());
        dto.setMaleF45t49LvpopCo(localPeople.getMaleF45t49LvpopCo());
        dto.setMaleF50t54LvpopCo(localPeople.getMaleF50t54LvpopCo());
        dto.setMaleF55t59LvpopCo(localPeople.getMaleF55t59LvpopCo());
        dto.setMaleF60t64LvpopCo(localPeople.getMaleF60t64LvpopCo());
        dto.setMaleF65t69LvpopCo(localPeople.getMaleF65t69LvpopCo());
        dto.setMaleF70t74LvpopCo(localPeople.getMaleF70t74LvpopCo());
        dto.setFemaleF0t9LvpopCo(localPeople.getFemaleF0t9LvpopCo());
        dto.setFemaleF10t14LvpopCo(localPeople.getFemaleF10t14LvpopCo());
        dto.setFemaleF15t19LvpopCo(localPeople.getFemaleF15t19LvpopCo());
        dto.setFemaleF20t24LvpopCo(localPeople.getFemaleF20t24LvpopCo());
        dto.setFemaleF25t29LvpopCo(localPeople.getFemaleF25t29LvpopCo());
        dto.setFemaleF30t34LvpopCo(localPeople.getFemaleF30t34LvpopCo());
        dto.setFemaleF35t39LvpopCo(localPeople.getFemaleF35t39LvpopCo());
        dto.setFemaleF40t44LvpopCo(localPeople.getFemaleF40t44LvpopCo());
        dto.setFemaleF45t49LvpopCo(localPeople.getFemaleF45t49LvpopCo());
        dto.setFemaleF50t54LvpopCo(localPeople.getFemaleF50t54LvpopCo());
        dto.setFemaleF55t59LvpopCo(localPeople.getFemaleF55t59LvpopCo());
        dto.setFemaleF60t64LvpopCo(localPeople.getFemaleF60t64LvpopCo());
        dto.setFemaleF65t69LvpopCo(localPeople.getFemaleF65t69LvpopCo());
        dto.setFemaleF70t74LvpopCo(localPeople.getFemaleF70t74LvpopCo());
        dto.setStdrDeId(localPeople.getStdrDeId());
        dto.setTmzonPdSe(localPeople.getTmzonPdSe());
        dto.setAdstrdCodeSe(localPeople.getAdstrdCodeSe());
        
        // 시간대 정보 설정
        setTimeZoneInfo(dto, localPeople.getTmzonPdSe());
        
        return dto;
    }
    
    // 시간대 코드를 사람이 읽기 쉬운 형태로 변환
    private void setTimeZoneInfo(LocalPeopleResponseDto dto, String tmzonPdSe) {
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
