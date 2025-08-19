package KT.PLIP.population.controller;

import KT.PLIP.population.domain.LongForeigner;
import KT.PLIP.population.dto.LongForeignerResponseDto;
import KT.PLIP.population.service.LongForeignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/population/long-foreigners")
@RequiredArgsConstructor
public class LongForeignerController {
    
    private final LongForeignerService longForeignerService;
    
    // 특정 행정동의 장기체류 외국인 목록 조회
    @GetMapping("/{adstrdCode}")
    public ResponseEntity<List<LongForeignerResponseDto>> getLongForeignersByAdstrdCode(
            @PathVariable String adstrdCode) {
        List<LongForeignerResponseDto> response = longForeignerService.getLongForeignersByAdstrdCode(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 특정 행정동의 장기체류 외국인 상세 통계
    @GetMapping("/{adstrdCode}/stats")
    public ResponseEntity<LongForeignerResponseDto> getLongForeignerStats(
            @PathVariable String adstrdCode) {
        LongForeignerResponseDto response = longForeignerService.getLongForeignerStats(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 모든 장기체류 외국인 데이터 조회
    @GetMapping
    public ResponseEntity<List<LongForeignerResponseDto>> getAllLongForeigners() {
        List<LongForeignerResponseDto> response = longForeignerService.getAllLongForeigners();
        return ResponseEntity.ok(response);
    }
}
