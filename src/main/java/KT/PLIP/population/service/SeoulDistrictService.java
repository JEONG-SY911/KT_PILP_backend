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
    
    // 새 구 추가
    @Transactional
    public SeoulDistrictResponseDto addDistrict(SeoulDistrictResponseDto requestDto) {
        // 중복 검사
        if (seoulDistrictRepository.existsByDistrictCode(requestDto.getDistrictCode())) {
            throw new RuntimeException("이미 존재하는 구 코드입니다: " + requestDto.getDistrictCode());
        }
        
        if (seoulDistrictRepository.existsByDistrictName(requestDto.getDistrictName())) {
            throw new RuntimeException("이미 존재하는 구 이름입니다: " + requestDto.getDistrictName());
        }
        
        SeoulDistrict district = new SeoulDistrict();
        district.setDistrictCode(requestDto.getDistrictCode());
        district.setDistrictName(requestDto.getDistrictName());
        district.setDistrictNameEng(requestDto.getDistrictNameEng());
        district.setDescription(requestDto.getDescription());
        district.setActive(true);
        district.setSortOrder(requestDto.getDistrictCode() != null ? 
            Integer.parseInt(requestDto.getDistrictCode()) : 999);
        
        SeoulDistrict savedDistrict = seoulDistrictRepository.save(district);
        return convertToDto(savedDistrict);
    }
    
    // 구 정보 수정
    @Transactional
    public SeoulDistrictResponseDto updateDistrict(String districtCode, SeoulDistrictResponseDto requestDto) {
        SeoulDistrict district = seoulDistrictRepository.findByDistrictCode(districtCode)
                .orElseThrow(() -> new RuntimeException("구를 찾을 수 없습니다: " + districtCode));
        
        district.setDistrictName(requestDto.getDistrictName());
        district.setDistrictNameEng(requestDto.getDistrictNameEng());
        district.setDescription(requestDto.getDescription());
        district.setActive(true);
        
        SeoulDistrict updatedDistrict = seoulDistrictRepository.save(district);
        return convertToDto(updatedDistrict);
    }
    
    // 구 비활성화 (삭제 대신)
    @Transactional
    public void deactivateDistrict(String districtCode) {
        SeoulDistrict district = seoulDistrictRepository.findByDistrictCode(districtCode)
                .orElseThrow(() -> new RuntimeException("구를 찾을 수 없습니다: " + districtCode));
        
        district.setActive(false);
        seoulDistrictRepository.save(district);
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
