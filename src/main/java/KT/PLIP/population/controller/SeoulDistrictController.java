package KT.PLIP.population.controller;

import KT.PLIP.population.dto.SeoulDistrictResponseDto;
import KT.PLIP.population.service.SeoulDistrictService;
import KT.PLIP.population.service.PopulationDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/population/seoul/districts")
@RequiredArgsConstructor
public class SeoulDistrictController {
    
    private final SeoulDistrictService seoulDistrictService;
    private final PopulationDetailService populationDetailService;
    
    // 모든 활성화된 구 조회 (실제 데이터베이스에 있는 구인지 확인)
    @GetMapping
    public ResponseEntity<List<SeoulDistrictResponseDto>> getAllActiveDistricts() {
        List<SeoulDistrictResponseDto> districts = populationDetailService.getAllDistricts();
        return ResponseEntity.ok(districts);
    }
    
    // 구 코드로 조회
    @GetMapping("/{districtCode}")
    public ResponseEntity<SeoulDistrictResponseDto> getDistrictByCode(@PathVariable String districtCode) {
        Optional<SeoulDistrictResponseDto> district = seoulDistrictService.getDistrictByCode(districtCode);
        return district.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // 구 이름으로 조회
    @GetMapping("/name/{districtName}")
    public ResponseEntity<SeoulDistrictResponseDto> getDistrictByName(@PathVariable String districtName) {
        Optional<SeoulDistrictResponseDto> district = seoulDistrictService.getDistrictByName(districtName);
        return district.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
