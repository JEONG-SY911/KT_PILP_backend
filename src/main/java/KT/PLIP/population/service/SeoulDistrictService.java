package KT.PLIP.population.service;

import KT.PLIP.population.domain.SeoulDistrict;
import KT.PLIP.population.dto.SeoulDistrictResponseDto;
import KT.PLIP.population.repository.SeoulDistrictRepository;
import KT.PLIP.population.service.PopulationDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeoulDistrictService {
    
    private final SeoulDistrictRepository seoulDistrictRepository;
    private final PopulationDetailService populationDetailService;
    
    // 모든 활성화된 구 조회 (실제 데이터베이스에 있는 구인지 확인)
    public List<SeoulDistrictResponseDto> getAllActiveDistricts() {
        return populationDetailService.getAllDistricts();
    }
    
    // 구 코드로 조회
    public Optional<SeoulDistrictResponseDto> getDistrictByCode(String districtCode) {
        return seoulDistrictRepository.findByDistrictCode(districtCode)
                .map(this::convertToDto);
    }
    
    // 구 이름으로 조회
    public Optional<SeoulDistrictResponseDto> getDistrictByName(String districtName) {
        return seoulDistrictRepository.findByDistrictName(districtName)
                .map(this::convertToDto);
    }
    
    
    
    // Entity를 DTO로 변환 (실제 데이터베이스에 있는 구인지 확인)
    private SeoulDistrictResponseDto convertToDto(SeoulDistrict district) {
        SeoulDistrictResponseDto dto = new SeoulDistrictResponseDto();
        dto.setDistrictName(district.getDistrictName());
        dto.setDistrictCode(district.getDistrictCode());
        dto.setDistrictNameEng(district.getDistrictNameEng());
        dto.setDescription(district.getDescription());
        
        // 실제 데이터베이스에 있는 구인지 확인
        List<String> availableDistricts = populationDetailService.getAvailableDistrictsFromDatabase();
        dto.setImplemented(availableDistricts.contains(district.getDistrictCode()));
        
        return dto;
    }
}
