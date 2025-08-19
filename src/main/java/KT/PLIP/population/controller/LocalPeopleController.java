package KT.PLIP.population.controller;

import KT.PLIP.population.dto.LocalPeopleResponseDto;
import KT.PLIP.population.service.LocalPeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/local-people")
@RequiredArgsConstructor
public class LocalPeopleController {
    
    private final LocalPeopleService localPeopleService;
    
    // 모든 인구 데이터 조회
    @GetMapping
    public ResponseEntity<List<LocalPeopleResponseDto>> getAllLocalPeople() {
        List<LocalPeopleResponseDto> response = localPeopleService.getAllLocalPeople();
        return ResponseEntity.ok(response);
    }
    
    // 행정동 코드로 조회
    @GetMapping("/code/{adstrdCode}")
    public ResponseEntity<List<LocalPeopleResponseDto>> getLocalPeopleByCode(
            @PathVariable String adstrdCode) {
        List<LocalPeopleResponseDto> response = localPeopleService.getLocalPeopleByAdstrdCode(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 최소 인구 수로 조회
    @GetMapping("/population")
    public ResponseEntity<List<LocalPeopleResponseDto>> getLocalPeopleByMinPopulation(
            @RequestParam Integer minPopulation) {
        List<LocalPeopleResponseDto> response = localPeopleService.getLocalPeopleByMinPopulation(minPopulation);
        return ResponseEntity.ok(response);
    }
    
    // 여러 행정동 코드로 조회 (POST 요청으로 리스트 전달)
    @PostMapping("/codes")
    public ResponseEntity<List<LocalPeopleResponseDto>> getLocalPeopleByCodes(
            @RequestBody List<String> adstrdCodes) {
        List<LocalPeopleResponseDto> response = localPeopleService.getLocalPeopleByAdstrdCodes(adstrdCodes);
        return ResponseEntity.ok(response);
    }
    
    // 강남구 특정 행정동들 조회 (Python 코드의 adstrd_codes와 동일)
    @GetMapping("/gangnam")
    public ResponseEntity<List<LocalPeopleResponseDto>> getGangnamLocalPeople() {
        List<String> gangnamCodes = List.of(
            "11680600", "11680610", "11680630", "11680640", 
            "11680650", "11680565", "11680510", "11680545"
        );
        List<LocalPeopleResponseDto> response = localPeopleService.getLocalPeopleByAdstrdCodes(gangnamCodes);
        return ResponseEntity.ok(response);
    }
}
