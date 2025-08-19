package KT.PLIP.population.controller;

import KT.PLIP.population.domain.TempForeigner;
import KT.PLIP.population.dto.TempForeignerResponseDto;
import KT.PLIP.population.service.TempForeignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/population/temp-foreigners")
@RequiredArgsConstructor
public class TempForeignerController {
    
    private final TempForeignerService tempForeignerService;
    
    // 특정 행정동의 단기체류 외국인 목록 조회
    @GetMapping("/{adstrdCode}")
    public ResponseEntity<List<TempForeignerResponseDto>> getTempForeignersByAdstrdCode(
            @PathVariable String adstrdCode) {
        List<TempForeignerResponseDto> response = tempForeignerService.getTempForeignersByAdstrdCode(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 특정 행정동의 단기체류 외국인 상세 통계
    @GetMapping("/{adstrdCode}/stats")
    public ResponseEntity<TempForeignerResponseDto> getTempForeignerStats(
            @PathVariable String adstrdCode) {
        TempForeignerResponseDto response = tempForeignerService.getTempForeignerStats(adstrdCode);
        return ResponseEntity.ok(response);
    }
    
    // 모든 단기체류 외국인 데이터 조회
    @GetMapping
    public ResponseEntity<List<TempForeignerResponseDto>> getAllTempForeigners() {
        List<TempForeignerResponseDto> response = tempForeignerService.getAllTempForeigners();
        return ResponseEntity.ok(response);
    }
}
